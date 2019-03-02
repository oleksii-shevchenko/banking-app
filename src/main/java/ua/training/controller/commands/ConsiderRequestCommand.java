package ua.training.controller.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * This method used to mark user account opening request as considered by admins.
 * @author Oleksii Shevchenko
 */
@Controller("considerRequest")
public class ConsiderRequestCommand implements Command {
    private UserService userService;
    private PathManager pathManager;

    @Override
    public String execute(HttpServletRequest request) {
        Long requestId = Long.valueOf(request.getParameter("requestId"));

        userService.considerRequest(requestId);

        return "redirect:" + pathManager.getPath("path.api.requests");
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
