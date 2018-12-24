package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.ValidationUtil;
import ua.training.controller.util.managers.ContentManager;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Currency;
import ua.training.model.entity.Transaction;
import ua.training.model.entity.User;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

public class MakeTransactionCommand implements Command {
    private static Logger logger = LogManager.getLogger(MakeTransactionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        User user = new UserService(JdbcDaoFactory.getInstance()).get((Long) request.getSession().getAttribute("id"));

        request.setAttribute("accountIds", user.getAccounts());
        request.setAttribute("currencies", Currency.values());

        ValidationUtil util = new ValidationUtil();
        if (!util.makeValidation(request, List.of("sender", "receiver", "amount", "currency"))) {
            logger.warn("User " + user.getId() + " input not valid data");

            return PathManager.getPath("path.make-transaction");
        }

        Transaction transaction = Transaction.getBuilder()
                .setSender(Long.valueOf(request.getParameter("sender")))
                .setReceiver(Long.valueOf(request.getParameter("receiver")))
                .setType(Transaction.Type.MANUAL)
                .setAmount(new BigDecimal(request.getParameter("amount")))
                .setCurrency(Currency.valueOf(request.getParameter("currency")))
                .build();

        if (!user.getAccounts().contains(transaction.getSender())) {
            logger.warn("User " + user.getId() + "try to access account " + transaction.getSender() + " without permissions");

            return "redirect:" + PathManager.getPath("path.error");
        }

        AccountService service = new AccountService(JdbcDaoFactory.getInstance());
        try {
            service.makeTransaction(transaction);
        } catch (NotEnoughMoneyException exception) {
            logger.warn(exception);

            ContentManager.setLocalizedMessage(request, "notEnough", "content.message.not.enough.money");

            return PathManager.getPath("path.make-transaction");
        }

        return PathManager.getPath("path.completed");
    }
}
