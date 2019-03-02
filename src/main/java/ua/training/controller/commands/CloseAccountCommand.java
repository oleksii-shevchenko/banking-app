package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.service.AccountService;

import javax.servlet.http.HttpServletRequest;

/**
 * This command used by admins to force closing user account after its end of expires when the balance of account is
 * not zero. Required params: accountId - id of account to close.
 * @author Oleksii Shevchenko
 */
@Controller("closeAccount")
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
