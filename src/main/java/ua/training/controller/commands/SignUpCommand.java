package ua.training.controller.commands;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.ValidationUtil;
import ua.training.controller.util.managers.ContentManager;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.entity.User;
import ua.training.model.exception.NonUniqueEmailException;
import ua.training.model.exception.NonUniqueLoginException;
import ua.training.model.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller("signUp")
public class SignUpCommand implements Command {
    private static Logger logger = LogManager.getLogger(SignUpCommand.class);

    private AuthenticationService authenticationService;
    private ValidationUtil validationUtil;
    private ContentManager contentManager;
    private PathManager pathManager;

    @Override
    public String execute(HttpServletRequest request) {
        if (!validationUtil.makeValidation(request, List.of("login", "pass", "email", "firstName", "secondName"))) {
            return pathManager.getPath("path.sign.up");
        }

        User user = buildUserFromRequest(request);

        try {
            user.setId(authenticationService.register(user));
        } catch (NonUniqueLoginException exception) {
            logger.warn("Tries to sign up with existing login " + user.getLogin());

            contentManager.setLocalizedMessage(request, "loginWrong", "content.message.exist.login");
            return pathManager.getPath("path.sign.up");
        } catch (NonUniqueEmailException exception) {
            logger.warn("Tries to sign in with existing email " + user.getEmail());

            contentManager.setLocalizedMessage(request, "emailWrong", "content.message.exist.email");
            return pathManager.getPath("path.sign.up");
        }

        signInUser(request, user);

        return "redirect:" + pathManager.getPath("path.index");
    }

    private User buildUserFromRequest(HttpServletRequest request) {
        return User.getBuilder()
                .setLogin(request.getParameter("login"))
                .setPasswordHash(DigestUtils.sha256Hex(request.getParameter("pass")))
                .setEmail(request.getParameter("email"))
                .setRole(User.Role.USER)
                .setFirstName(request.getParameter("firstName"))
                .setSecondName(request.getParameter("secondName"))
                .build();
    }

    private void signInUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute("login", user.getLogin());
        request.getSession().setAttribute("role", user.getRole().name());
        request.getSession().setAttribute("id", user.getId());
        request.getServletContext().setAttribute(user.getLogin(), request.getSession());

        logger.info("User " + user.getLogin() + " is signed in");
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
