package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.entity.Account;
import ua.training.model.entity.Currency;
import ua.training.model.entity.User;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller("infoAccount")
public class InfoAccountCommand implements Command {
    private static Logger logger = LogManager.getLogger(InfoAccountCommand.class);

    private AccountService accountService;
    private UserService userService;

    private PathManager pathManager;

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        Long userId = (Long) request.getSession().getAttribute("id");
        User.Role role = User.Role.valueOf((String) request.getSession().getAttribute("role"));

        Account account = accountService.getAccount(accountId);
        User user = userService.get(userId);

        if (!role.equals(User.Role.ADMIN) && !account.getHolders().contains(userId)) {
            logger.warn("User " + user.getId() + "try to access account " + accountId + " without permissions");

            return "redirect:" + pathManager.getPath("path.error");
        }

        request.setAttribute("type", account.getClass().getSimpleName());
        request.setAttribute("account", account);
        request.setAttribute("currencies", Currency.values());
        request.setAttribute("accountIds", user.getAccounts());

        return pathManager.getPath("path.info.account");
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }
}
