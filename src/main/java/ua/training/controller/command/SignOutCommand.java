package ua.training.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.managers.PathManager;

import javax.servlet.http.HttpServletRequest;

public class SignOutCommand implements Command {
    private static Logger logger = LogManager.getLogger(SignOutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("User " + request.getSession().getAttribute("login") + "is sign out.");

        request.getServletContext().removeAttribute((String) request.getSession().getAttribute("login"));
        request.getSession().invalidate();

        return "redirect:" + PathManager.getPath("path.index");
    }
}
