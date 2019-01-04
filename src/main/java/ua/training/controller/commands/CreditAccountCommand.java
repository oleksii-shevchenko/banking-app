package ua.training.controller.commands;

import ua.training.controller.util.ValidationUtil;
import ua.training.controller.util.managers.ContentManager;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.CreditAccount;
import ua.training.model.entity.Currency;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Command used by admins to open accounts for users. Required params: intiDeposit, expiresEnd, creditLimit, creditRate,
 * requestId.
 * @see OpenAccountCommand
 * @author Oleksii Shevchenko
 */
public class CreditAccountCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        ValidationUtil util = new ValidationUtil();

        if (!util.makeValidation(request, List.of("initDeposit", "expiresEnd", "creditLimit", "creditRate"))) {
            return new ProcessRequestCommand().execute(request);
        }

        CreditAccount account = CreditAccount.getBuilder()
                .setCurrency(Currency.valueOf(request.getParameter("currency")))
                .setBalance(new BigDecimal(request.getParameter("initDeposit")))
                .setExpiresEnd(LocalDate.parse(request.getParameter("expiresEnd")))
                .setStatus(Account.Status.ACTIVE)
                .setCreditRate(new BigDecimal(request.getParameter("creditRate")))
                .setCreditLimit(new BigDecimal(request.getParameter("creditLimit")))
                .build();

        if (account.getExpiresEnd().isBefore(LocalDate.now())) {
            ContentManager.setLocalizedMessage(request, "isBeforeNow", "content.message.date.before");
            return new ProcessRequestCommand().execute(request);
        }

        Long requestId = Long.valueOf(request.getParameter("requestId"));

        new UserService(JdbcDaoFactory.getInstance()).completeOpeningRequest(requestId, account);

        return PathManager.getPath("path.completed");
    }
}
