package ua.training.controller.commands;

import org.springframework.stereotype.Controller;
import ua.training.controller.util.ValidationUtil;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Currency;
import ua.training.model.entity.Request;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller("request")
public class RequestCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        UserService service = new UserService(JdbcDaoFactory.getInstance());
        ValidationUtil util = new ValidationUtil();

        if (!util.makeValidation(request, List.of("account", "currency"))) {
            return PathManager.getPath("path.opening-request");
        }

        Request userRequest = Request.getBuilder()
                .setRequesterId((Long) request.getSession().getAttribute("id"))
                .setCurrency(Currency.valueOf(request.getParameter("currency")))
                .setType(Request.Type.valueOf(request.getParameter("account")))
                .setConsidered(false)
                .build();

        service.makeRequest(userRequest);

        return "redirect:" + PathManager.getPath("path.completed");
    }
}
