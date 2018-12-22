package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Account;
import ua.training.model.service.AccountService;

import javax.servlet.http.HttpServletRequest;

public class InfoAccountCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        AccountService accountService = new AccountService(JdbcDaoFactory.getInstance());

        Long accountId = Long.valueOf(request.getParameter("id"));
        Long userId = (Long) request.getSession().getAttribute("id");

        Account account = accountService.getAccount(accountId);

        if (!account.getHolders().contains(userId)) {
            return "redirect:" + PathManager.getPath("path.error");
        }

        request.setAttribute("type", account.getClass().getSimpleName());
        request.setAttribute("account", account);

        return PathManager.getPath("path.info.account");
    }
}
