package ua.training.controller.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;

import javax.servlet.http.HttpServletRequest;

/**
 * This command used to change content language by any user. The current language of user puts in user session.
 * Required params: lang - tag of one of possible languages.
 * @author Oleksii Shevchenko
 */
@Controller("changeLanguage")
public class ChangeLanguageCommand implements Command {
    private PathManager manager;

    @Autowired
    public void setManager(PathManager manager) {
        this.manager = manager;
    }


    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute("lang", request.getParameter("lang"));
        return "redirect:" + manager.getPath("path.index");
    }
}
