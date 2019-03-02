package ua.training.controller.commands;

import org.springframework.stereotype.Controller;
import ua.training.controller.util.ValidationUtil;
import ua.training.controller.util.managers.ContentManager;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.Currency;
import ua.training.model.entity.DepositAccount;
import ua.training.model.service.ScheduledTaskService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Command used by admins to open accounts for users. Required params: intiDeposit, expiresEnd, updatePeriod, depositRate,
 * requestId.
 * @see OpenAccountCommand
 * @author Oleksii Shevchenko
 */
@Controller("depositAccount")
public class DepositAccountCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        ValidationUtil util = new ValidationUtil();

        if (!util.makeValidation(request, List.of("initDeposit", "expiresEnd", "depositRate", "updatePeriod"))) {
            return new ProcessRequestCommand().execute(request);
        }
        DepositAccount account = DepositAccount.getBuilder()
                .setCurrency(Currency.valueOf(request.getParameter("currency")))
                .setBalance(new BigDecimal(request.getParameter("initDeposit")))
                .setExpiresEnd(LocalDate.parse(request.getParameter("expiresEnd")))
                .setStatus(Account.Status.ACTIVE)
                .setUpdatePeriod(Integer.parseInt(request.getParameter("updatePeriod")))
                .setDepositRate(new BigDecimal(request.getParameter("depositRate")))
                .build();

        if (account.getExpiresEnd().isBefore(LocalDate.now())) {
            ContentManager.setLocalizedMessage(request, "isBeforeNow", "content.message.date.before");
            return new ProcessRequestCommand().execute(request);
        }

        Long requestId = Long.valueOf(request.getParameter("requestId"));

        account.setId(new UserService(JdbcDaoFactory.getInstance()).completeOpeningRequest(requestId, account));

        new ScheduledTaskService().registerDeposit(account, JdbcDaoFactory.getInstance());

        return PathManager.getPath("path.completed");
    }
}
