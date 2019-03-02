package ua.training.controller.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller("profile")
public class ProfileCommand implements Command {
    private UserService userService;

    private PathManager pathManager;

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("user", userService.get((Long) request.getSession().getAttribute("id")));

        return pathManager.getPath("path.profile");
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
