package ua.training.controller.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.ContentManager;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.entity.Request;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller("processRequest")
public class ProcessRequestCommand implements Command {
    private final long standardLifePeriod;

    private UserService userService;

    private ContentManager contentManager;
    private PathManager pathManager;

    public ProcessRequestCommand(@Value("4") long standardLifePeriod) {
        this.standardLifePeriod = standardLifePeriod;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Long requestId = Long.valueOf(request.getParameter("requestId"));

        Request userRequest = userService.getRequest(requestId);

        contentManager.setLocalizedMessage(request, "type", userRequest.getType().getMassageKey());

        request.setAttribute("request", userRequest);
        request.setAttribute("now", LocalDate.now().plusYears(standardLifePeriod));

        return pathManager.getPath("path.request");
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
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
