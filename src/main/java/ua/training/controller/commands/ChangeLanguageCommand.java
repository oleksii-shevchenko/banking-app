package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;

import javax.servlet.http.HttpServletRequest;

public class ChangeLanguageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute("lang", request.getParameter("lang"));
        return "redirect:" + PathManager.getPath("path.index");
    }
}
