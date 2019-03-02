package ua.training.controller.commands;

import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.ContentManager;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Request;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller("processRequest")
public class ProcessRequestCommand implements Command {
    private final long STANDARD_LIFE_PERIOD = 4;

    @Override
    public String execute(HttpServletRequest request) {
        Long requestId = Long.valueOf(request.getParameter("requestId"));

        Request userRequest = new UserService(JdbcDaoFactory.getInstance()).getRequest(requestId);

        ContentManager.setLocalizedMessage(request, "type", userRequest.getType().getMassageKey());

        request.setAttribute("request", userRequest);
        request.setAttribute("now", LocalDate.now().plusYears(STANDARD_LIFE_PERIOD));

        return PathManager.getPath("path.request");
    }
}
