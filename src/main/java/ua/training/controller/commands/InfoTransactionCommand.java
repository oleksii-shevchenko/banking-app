package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Transaction;
import ua.training.model.entity.User;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class InfoTransactionCommand implements Command {
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
            return "redirect:" + PathManager.getPath("path.error");
        }

        request.setAttribute("transaction", transaction);

        return PathManager.getPath("path.transaction");
    }
}
