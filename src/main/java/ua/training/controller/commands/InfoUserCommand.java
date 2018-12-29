package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class InfoUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        if (Objects.nonNull(request.getParameter("userId"))) {
            Long userId = Long.valueOf(request.getParameter("userId"));
            request.setAttribute("user", new UserService(JdbcDaoFactory.getInstance()).get(userId));
        }

        return PathManager.getPath("path.user.info");
    }
}
