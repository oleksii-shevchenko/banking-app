package ua.training.controller.commands;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.ValidationUtil;
import ua.training.controller.util.managers.ContentManager;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.User;
import ua.training.model.exception.NonUniqueEmailException;
import ua.training.model.exception.NonUniqueLoginException;
import ua.training.model.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SignUpCommand implements Command {
    private static Logger logger = LogManager.getLogger(SignUpCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        AuthenticationService service = new AuthenticationService(new JdbcDaoFactory().getUserDao());

        if (isNotValid(request, List.of("login", "pass", "email", "firstName", "secondName"))) {
            return PathManager.getPath("path.sign.up");
        }

        User user = buildUserFromRequest(request);

        try {
            service.register(user);
        } catch (NonUniqueLoginException exception) {
            logger.warn("Tries to sign up with existing login " + user.getLogin());

            ContentManager.setLocalizedMessage(request, "loginWrong", "content.message.exist.login");
            return PathManager.getPath("path.sign.up");
        } catch (NonUniqueEmailException exception) {
            logger.warn("Tries to sign in with existing email " + user.getEmail());

            ContentManager.setLocalizedMessage(request, "emailWrong", "content.message.exist.email");
            return PathManager.getPath("path.sign.up");
        }

        signInUser(request, user);

        return "redirect:" + PathManager.getPath("path.index");
    }

    private boolean isNotValid(HttpServletRequest request, List<String> params) {
        ValidationUtil util = new ValidationUtil();

        boolean flag = false;
        for (String param : params) {
            flag |= (util.isEmpty(request, param) || !util.isValid(request, param));
        }

        return flag;
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
}
