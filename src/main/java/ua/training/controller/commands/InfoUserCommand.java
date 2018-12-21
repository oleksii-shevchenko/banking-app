package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;

import javax.servlet.http.HttpServletRequest;

public class InfoUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {

        //request.setAttribute("user", new UserService(JdbcDaoFactory.getInstance()).getByLogin((String) request.getSession().getAttribute("login")));
        return PathManager.getPath("path.user.profile");
    }
}
