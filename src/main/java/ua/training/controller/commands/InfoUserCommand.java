package ua.training.controller.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller("infoUser")
public class InfoUserCommand implements Command {
    private PathManager pathManager;

    private UserService userService;

    @Override
    public String execute(HttpServletRequest request) {
        if (Objects.nonNull(request.getParameter("userId"))) {
            Long userId = Long.valueOf(request.getParameter("userId"));
            request.setAttribute("user", userService.get(userId));
        }

        return pathManager.getPath("path.user.info");
    }

    @Autowired
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
