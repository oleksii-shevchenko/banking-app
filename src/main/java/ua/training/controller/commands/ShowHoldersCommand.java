package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class ShowHoldersCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("id"));

        request.setAttribute("masterAccount", accountId);
        request.setAttribute("permission", new UserService(JdbcDaoFactory.getInstance()).getPermission((Long) request.getSession().getAttribute("id"), accountId));
        request.setAttribute("holders", new UserService(JdbcDaoFactory.getInstance()).getAccountHolders(accountId));

        return PathManager.getPath("path.holders");
    }
}
