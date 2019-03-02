package ua.training.controller.commands;

import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller("profile")
public class ProfileCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        UserService service = new UserService(JdbcDaoFactory.getInstance());

        request.setAttribute("user", service.get((Long) request.getSession().getAttribute("id")));

        return PathManager.getPath("path.profile");
    }
}
