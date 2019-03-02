package ua.training.controller.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.service.AccountService;

import javax.servlet.http.HttpServletRequest;

@Controller("showAccounts")
public class ShowAccountsCommand implements Command {
    private AccountService accountService;
    private PathManager pathManager;

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("accounts", accountService.getAccounts((Long) request.getSession().getAttribute("id")));

        return pathManager.getPath("path.all-accounts");
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
