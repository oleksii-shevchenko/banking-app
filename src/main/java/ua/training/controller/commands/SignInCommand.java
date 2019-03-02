package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.ValidationUtil;
import ua.training.controller.util.managers.ContentManager;
import ua.training.controller.util.managers.PathManager;
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

    private AuthenticationService authenticationService;
    private ValidationUtil validationUtil;
    private ContentManager contentManager;
    private PathManager pathManager;

    @Override
    public String execute(HttpServletRequest request) {
        if (!validationUtil.makeValidation(request, List.of("login", "pass"))) {
            return pathManager.getPath("path.sign.in");
        }

        String login = request.getParameter("login");
        String password = request.getParameter("pass");

        try {
            User user = authenticationService.authenticate(login, password);

            invalidateOtherSessions(request, user);
            signInUser(request, user);
        } catch (NoSuchUserException exception) {
            logger.warn("Tries to sign in with wrong login " + login);

            contentManager.setLocalizedMessage(request, "loginWrong", "content.message.wrong.login");
            return pathManager.getPath("path.sign.in");
        } catch (WrongPasswordException exception) {
            logger.warn("User " + login + " tries to sign in with wrong password");

            contentManager.setLocalizedMessage(request, "passWrong", "content.message.wrong.pass");
            return pathManager.getPath("path.sign.in");
        }

        return "redirect:" + pathManager.getPath("path.index");
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

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Autowired
    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setContentManager(ContentManager contentManager) {
        this.contentManager = contentManager;
    }

    @Autowired
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }
}
