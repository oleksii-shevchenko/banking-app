package ua.training.controller.command;

import ua.training.controller.util.PathManager;

import javax.servlet.http.HttpServletRequest;

public class ChangeLanguageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute("lang", request.getParameter("lang"));
        return PathManager.getPath("path.index");
    }
}
