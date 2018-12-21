package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.User;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class InfoAccountCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        AccountService accountService = new AccountService(JdbcDaoFactory.getInstance());
        UserService userService = new UserService(JdbcDaoFactory.getInstance());

        User user = userService.get((Long) (request.getSession().getAttribute("id")));

        Long accountId = Long.valueOf(request.getParameter("id"));

        if (!user.getAccounts().contains(accountId)) {
            return "redirect:" + PathManager.getPath("path.error");
        }

        Account account = accountService.getAccount(Long.valueOf(request.getParameter("id")));

        request.setAttribute("type", account.getClass().getSimpleName());
        request.setAttribute("account", account);

        return PathManager.getPath("path.info.account");
    }
}
