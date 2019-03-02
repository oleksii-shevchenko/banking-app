package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.entity.User;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller("showTransactions")
public class ShowTransactionsCommand implements Command {
    private static Logger logger = LogManager.getLogger(ShowTransactionsCommand.class);

    private final int itemsNumber;

    private UserService userService;
    private AccountService accountService;
    private PathManager pathManager;

    public ShowTransactionsCommand(@Value("5") int itemsNumber) {
        this.itemsNumber = itemsNumber;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));

        User user = userService.get((Long) request.getSession().getAttribute("id"));
        if (!user.getAccounts().contains(accountId)) {
            logger.warn("User " + user.getId() + "try to access account " + accountId + " without permissions");

            return "redirect:" + pathManager.getPath("path.error");
        }

        int page = Objects.isNull(request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));

        request.setAttribute("masterAccount", accountId);
        request.setAttribute("page", accountService.getTransactionsPage(accountId, itemsNumber, page));

        return pathManager.getPath("path.all-transactions");
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
