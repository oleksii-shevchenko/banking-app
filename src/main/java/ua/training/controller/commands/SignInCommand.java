package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.ValidationUtil;
import ua.training.controller.util.managers.ContentManager;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.User;
import ua.training.model.exception.NoSuchUserException;
import ua.training.model.exception.WrongPasswordException;
import ua.training.model.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller("signIn")
public class SignInCommand implements Command {
    private static Logger logger = LogManager.getLogger(SignInCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        AuthenticationService service = new AuthenticationService(new JdbcDaoFactory().getUserDao());
        ValidationUtil util = new ValidationUtil();

        if (!util.makeValidation(request, List.of("login", "pass"))) {
            return PathManager.getPath("path.sign.in");
        }

        String login = request.getParameter("login");
        String password = request.getParameter("pass");

        try {
            User user = service.authenticate(login, password);

            invalidateOtherSessions(request, user);
            signInUser(request, user);
        } catch (NoSuchUserException exception) {
            logger.warn("Tries to sign in with wrong login " + login);

            ContentManager.setLocalizedMessage(request, "loginWrong", "content.message.wrong.login");
            return PathManager.getPath("path.sign.in");
        } catch (WrongPasswordException exception) {
            logger.warn("User " + login + " tries to sign in with wrong password");

            ContentManager.setLocalizedMessage(request, "passWrong", "content.message.wrong.pass");
            return PathManager.getPath("path.sign.in");
        }

        return "redirect:" + PathManager.getPath("path.index");
    }

    private void signInUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute("login", user.getLogin());
        request.getSession().setAttribute("role", user.getRole().name());
        request.getSession().setAttribute("id", user.getId());
        request.getServletContext().setAttribute(user.getLogin(), request.getSession());

        logger.info("User " + user.getLogin() + " is signed in");
    }

    private void invalidateOtherSessions(HttpServletRequest request, User user) {
        if (Objects.nonNull(request.getServletContext().getAttribute(user.getLogin()))) {
            ((HttpSession) request.getServletContext().getAttribute(user.getLogin())).invalidate();
            request.getServletContext().removeAttribute(user.getLogin());
        }

        logger.warn("Closed another session of user " + user.getLogin());
    }
}
