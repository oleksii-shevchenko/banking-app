package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.ValidationUtil;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Currency;
import ua.training.model.entity.Transaction;
import ua.training.model.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Controller("replenishAccount")
public class ReplenishAccountCommand implements Command {
    private static Logger logger = LogManager.getLogger(ReplenishAccountCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));

        ValidationUtil util = new ValidationUtil();
        if (!util.makeValidation(request, List.of("amount"))) {
            request.setAttribute("amountInvalid", true);
            request.setAttribute("account", new AccountService(JdbcDaoFactory.getInstance()).getAccount(accountId));
            request.setAttribute("currencies", Currency.values());
            return PathManager.getPath("path.info.account");
        }

        Transaction transaction = Transaction.getBuilder()
                .setSender(accountId)
                .setReceiver(accountId)
                .setType(Transaction.Type.EXTERNAL)
                .setAmount(new BigDecimal(request.getParameter("amount")))
                .setCurrency(Currency.valueOf(request.getParameter("currency")))
                .build();

        new AccountService(JdbcDaoFactory.getInstance()).makeTransaction(transaction);

        logger.info("Admin " + request.getSession().getAttribute("id") + " made account " + accountId + " replenishment");

        return "redirect:" + PathManager.getPath("path.completed");
    }
}
