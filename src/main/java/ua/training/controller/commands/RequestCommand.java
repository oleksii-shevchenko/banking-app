package ua.training.controller.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.ValidationUtil;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.entity.Currency;
import ua.training.model.entity.Request;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller("request")
public class RequestCommand implements Command {
    private UserService userService;
    private ValidationUtil validationUtil;
    private PathManager pathManager;

    @Override
    public String execute(HttpServletRequest request) {
        if (!validationUtil.makeValidation(request, List.of("account", "currency"))) {
            return pathManager.getPath("path.opening-request");
        }

        Request userRequest = Request.getBuilder()
                .setRequesterId((Long) request.getSession().getAttribute("id"))
                .setCurrency(Currency.valueOf(request.getParameter("currency")))
                .setType(Request.Type.valueOf(request.getParameter("account")))
                .setConsidered(false)
                .build();

        userService.makeRequest(userRequest);

        return "redirect:" + pathManager.getPath("path.completed");
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }
}
