package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.Currency;
import ua.training.model.entity.User;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class InfoAccountCommand implements Command {
    private static Logger logger = LogManager.getLogger(InfoAccountCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        Long userId = (Long) request.getSession().getAttribute("id");
        User.Role role = User.Role.valueOf((String) request.getSession().getAttribute("role"));

        Account account = new AccountService(JdbcDaoFactory.getInstance()).getAccount(accountId);
        User user = new UserService(JdbcDaoFactory.getInstance()).get(userId);

        if (!role.equals(User.Role.ADMIN) && !account.getHolders().contains(userId)) {
            logger.warn("User " + user.getId() + "try to access account " + accountId + " without permissions");

            return "redirect:" + PathManager.getPath("path.error");
        }

        request.setAttribute("type", account.getClass().getSimpleName());
        request.setAttribute("account", account);
        request.setAttribute("currencies", Currency.values());
        request.setAttribute("accountIds", user.getAccounts());

        return PathManager.getPath("path.info.account");
    }
}
