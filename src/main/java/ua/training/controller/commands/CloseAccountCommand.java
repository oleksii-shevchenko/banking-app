package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
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

    private AccountService service;
    private PathManager manager;

    @Autowired
    public void setService(AccountService service) {
        this.service = service;
    }

    @Autowired
    public void setManager(PathManager manager) {
        this.manager = manager;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        service.accountForceClosing(accountId);

        logger.info("Admin " + request.getSession().getAttribute("id") + " close account " + accountId);

        return manager.getPath("path.completed");
    }
}
