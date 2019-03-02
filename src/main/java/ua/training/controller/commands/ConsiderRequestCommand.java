package ua.training.controller.commands;

import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * This method used to mark user account opening request as considered by admins.
 * @author Oleksii Shevchenko
 */
@Controller("considerRequest")
public class ConsiderRequestCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        Long requestId = Long.valueOf(request.getParameter("requestId"));

        new UserService(JdbcDaoFactory.getInstance()).considerRequest(requestId);

        return "redirect:" + PathManager.getPath("path.api.requests");
    }
}
