package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;

import javax.servlet.http.HttpServletRequest;

/**
 * This command used to change content language by any user. The current language of user puts in user session.
 * Required params: lang - tag of one of possible languages.
 * @author Oleksii Shevchenko
 */
public class ChangeLanguageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute("lang", request.getParameter("lang"));
        return "redirect:" + PathManager.getPath("path.index");
    }
}
