package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.entity.Permission;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller("removeHolder")
public class RemoveHolderCommand implements Command {
    private static Logger logger = LogManager.getLogger(RemoveHolderCommand.class);

    private UserService userService;

    private PathManager pathManager;

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        Long userId = (Long) request.getSession().getAttribute("id");

        if (Objects.isNull(request.getParameter("holderId"))) {
            userService.removeHolder(userId, accountId);

            logger.info("User " + userId + " remove himself from holders of account " + accountId);

            return "redirect:" + pathManager.getPath("path.completed");
        }

        if (userService.getPermission(userId, accountId).equals(Permission.RESTRICTED)) {
            logger.warn("User " + userId + "try to access account " + accountId + " without permissions");

            return "redirect:" + pathManager.getPath("path.error");
        }

        userService.removeHolder(Long.valueOf(request.getParameter("holderId")), accountId);

        return "redirect:" + pathManager.getPath("path.completed");
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }
}
