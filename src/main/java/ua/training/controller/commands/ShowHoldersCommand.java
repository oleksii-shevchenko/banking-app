package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.User;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class ShowHoldersCommand implements Command {
    private static Logger logger = LogManager.getLogger(ShowHoldersCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));

        User user = new UserService(JdbcDaoFactory.getInstance()).get((Long) request.getSession().getAttribute("id"));

        if (!user.getAccounts().contains(accountId)) {
            logger.warn("User " + user.getId() + "try to access account " + accountId + " without permissions");

            return "redirect:" + PathManager.getPath("path.error");
        }

        request.setAttribute("masterAccount", accountId);
        request.setAttribute("permission", new UserService(JdbcDaoFactory.getInstance()).getPermission((Long) request.getSession().getAttribute("id"), accountId));
        request.setAttribute("holders", new UserService(JdbcDaoFactory.getInstance()).getAccountHolders(accountId));

        return PathManager.getPath("path.holders");
    }
}
