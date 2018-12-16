package ua.training.controller.command;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.ContentManager;
import ua.training.controller.util.PathManager;
import ua.training.controller.util.ValidationUtil;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.User;
import ua.training.model.exception.NonUniqueEmailException;
import ua.training.model.exception.NonUniqueLoginException;
import ua.training.model.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

public class SignUpCommand implements Command {
    private static Logger logger = LogManager.getLogger(SignUpCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        AuthenticationService service = new AuthenticationService(new JdbcDaoFactory().getUserDao());
        ContentManager contentManager = new ContentManager();

        if (hasEmptyParam(request)) {
            return PathManager.getPath("path.sign.up");
        }

        if (!isRequestValid(request)) {
            return PathManager.getPath("path.sign.up");
        }

        User user = buildUserFromRequest(request);

        try {
            service.registerUser(user);
        } catch (NonUniqueLoginException exception) {
            logger.warn("Tries to sign up with existing login " + user.getLogin());

            contentManager.setMessage(request, "wronglogin", "content.message.exist.login");
            return PathManager.getPath("path.sign.up");
        } catch (NonUniqueEmailException exception) {
            logger.warn("Tries to sign in with existing email " + user.getEmail());

            contentManager.setMessage(request, "wrongemail", "content.message.exist.email");
            return PathManager.getPath("path.sign.up");
        }

        signInUser(request, user);

        return PathManager.getPath("path.index");
    }

    private boolean isRequestValid(HttpServletRequest request) {
        ValidationUtil util = new ValidationUtil();
        return util.isValid(request, "login")
                & util.isValid(request, "pass")
                & util.isValid(request, "email")
                & util.isValid(request, "firstname")
                & util.isValid(request, "secondname");
    }

    private User buildUserFromRequest(HttpServletRequest request) {
        return User.getBuilder()
                .setLogin(request.getParameter("login"))
                .setPasswordHash(DigestUtils.sha256Hex(request.getParameter("pass")))
                .setEmail(request.getParameter("email"))
                .setRole(User.Role.USER)
                .setFirstName(request.getParameter("firstname"))
                .setSecondName(request.getParameter("secondname"))
                .build();
    }

    private void signInUser(HttpServletRequest request, User user) {
        logger.info("User " + user.getLogin() + " is signed in");

        request.getSession().setAttribute("login", user.getLogin());
        request.getSession().setAttribute("role", user.getRole().name());
        request.getServletContext().setAttribute(user.getLogin(), request.getSession());
    }

    private boolean hasEmptyParam(HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();

        boolean flag = true;
        while (names.hasMoreElements()) {
            flag &= Objects.isNull(request.getParameter(names.nextElement()));
        }

        return flag;
    }
}
