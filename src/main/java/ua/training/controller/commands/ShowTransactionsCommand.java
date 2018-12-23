package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.User;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ShowTransactionsCommand implements Command {
    private final int ITEMS_NUMBER = 5;

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));

        User user = new UserService(JdbcDaoFactory.getInstance()).get((Long) request.getSession().getAttribute("id"));
        if (!user.getAccounts().contains(accountId)) {
            return "redirect:" + PathManager.getPath("path.error");
        }

        int page = Objects.isNull(request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));

        request.setAttribute("masterAccount", accountId);
        request.setAttribute("page", new AccountService(JdbcDaoFactory.getInstance()).getTransactionsPage(accountId, ITEMS_NUMBER, page));

        return PathManager.getPath("path.all-transactions");
    }
}
