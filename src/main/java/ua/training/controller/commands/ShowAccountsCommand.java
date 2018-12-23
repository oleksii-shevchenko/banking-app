package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.service.AccountService;

import javax.servlet.http.HttpServletRequest;

public class ShowAccountsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        AccountService service = new AccountService(JdbcDaoFactory.getInstance());

        request.setAttribute("accounts", service.getAccounts((Long) request.getSession().getAttribute("id")));

        return PathManager.getPath("path.all-accounts");
    }
}
