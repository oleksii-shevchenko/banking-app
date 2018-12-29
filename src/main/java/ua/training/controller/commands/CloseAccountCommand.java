package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.service.AccountService;

import javax.servlet.http.HttpServletRequest;

public class CloseAccountCommand implements Command {
    private static Logger logger = LogManager.getLogger(CloseAccountCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        new AccountService(JdbcDaoFactory.getInstance()).accountForceClosing(accountId);

        logger.info("Admin " + request.getSession().getAttribute("id") + " close account " + accountId);

        return PathManager.getPath("path.completed");
    }
}
