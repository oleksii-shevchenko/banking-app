package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Transaction;
import ua.training.model.entity.User;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller("infoTransaction")
public class InfoTransactionCommand implements Command {
    private static Logger logger = LogManager.getLogger(InfoTransactionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        Long transactionId = Long.valueOf(request.getParameter("transactionId"));

        if (Objects.nonNull(request.getParameter("masterAccount"))) {
            request.setAttribute("masterAccount", Long.valueOf(request.getParameter("masterAccount")));
        }

        User user = new UserService(JdbcDaoFactory.getInstance()).get((Long) request.getSession().getAttribute("id"));
        Transaction transaction = new AccountService(JdbcDaoFactory.getInstance()).getTransaction(transactionId);

        if (!user.getRole().equals(User.Role.ADMIN) &&
                !(user.getAccounts().contains(transaction.getSender()) ||
                user.getAccounts().contains(transaction.getReceiver()))) {
            logger.warn("User " + user.getId() + "try to see info about transaction " + transactionId + " without permissions");

            return "redirect:" + PathManager.getPath("path.error");
        }

        request.setAttribute("transaction", transaction);

        return PathManager.getPath("path.transaction");
    }
}
