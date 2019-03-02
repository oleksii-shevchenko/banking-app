package ua.training.controller.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller("showRequests")
public class ShowRequestsCommand implements Command {
    private final int itemsNumber;

    private AccountService accountService;
    private PathManager pathManager;

    public ShowRequestsCommand(@Value("5") int itemsNumber) {
        this.itemsNumber = itemsNumber;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int page = Objects.isNull(request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));

        request.setAttribute("page", accountService.getRequestsPage(itemsNumber, page));

        return pathManager.getPath("path.all-requests");
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }
}
