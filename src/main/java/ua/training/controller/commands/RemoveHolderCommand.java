package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Permission;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class RemoveHolderCommand implements Command {
    private static Logger logger = LogManager.getLogger(RemoveHolderCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        UserService service = new UserService(JdbcDaoFactory.getInstance());

        Long accountId = Long.valueOf(request.getParameter("accountId"));
        Long userId = (Long) request.getSession().getAttribute("id");

        if (Objects.isNull(request.getParameter("holderId"))) {
            service.removeHolder(userId, accountId);

            logger.info("User " + userId + " remove himself from holders of account " + accountId);

            return "redirect:" + PathManager.getPath("path.completed");
        }

        if (service.getPermission(userId, accountId).equals(Permission.RESTRICTED)) {
            logger.warn("User " + userId + "try to access account " + accountId + " without permissions");

            return "redirect:" + PathManager.getPath("path.error");
        }

        service.removeHolder(Long.valueOf(request.getParameter("holderId")), accountId);

        return "redirect:" + PathManager.getPath("path.completed");
    }
}
