package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.User;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * This command used to new holder to active account by one of the valid holders. Required params: accountId;
 * holderId - id of the new holder.
 * @author Oleksii Shevchenko
 */
@Controller("addHolder")
public class AddHolderCommand implements Command {
    private static Logger logger = LogManager.getLogger(AddHolderCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        User user = new UserService(JdbcDaoFactory.getInstance()).get((Long) request.getSession().getAttribute("id"));

        Long accountId = Long.valueOf(request.getParameter("accountId"));
        Long holderId = Long.valueOf(request.getParameter("holderId"));

        if (!user.getAccounts().contains(accountId)) {
            logger.warn("User " + user.getId() + "try to access account " + accountId + " without permissions");

            return "redirect:" + PathManager.getPath("path.error");
        }

        new UserService(JdbcDaoFactory.getInstance()).addHolder(holderId, accountId);

        logger.info("User " + holderId + " was added as holder to account " + accountId);

        return "redirect:" + PathManager.getPath("path.completed");
    }
}
