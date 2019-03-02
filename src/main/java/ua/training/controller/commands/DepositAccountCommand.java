package ua.training.controller.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.ValidationUtil;
import ua.training.controller.util.managers.ContentManager;
import ua.training.controller.util.managers.PathManager;
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
    private ValidationUtil validationUtil;
    private ContentManager contentManager;
    private PathManager pathManager;

    private UserService userService;
    private ScheduledTaskService scheduledTaskService;

    private Command command;

    @Override
    public String execute(HttpServletRequest request) {
        if (!validationUtil.makeValidation(request, List.of("initDeposit", "expiresEnd", "depositRate", "updatePeriod"))) {
            return command.execute(request);
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
            contentManager.setLocalizedMessage(request, "isBeforeNow", "content.message.date.before");
            return command.execute(request);
        }

        Long requestId = Long.valueOf(request.getParameter("requestId"));

        account.setId(userService.completeOpeningRequest(requestId, account));

        scheduledTaskService.registerDeposit(account);

        return pathManager.getPath("path.completed");
    }

    @Autowired
    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setContentManager(ContentManager contentManager) {
        this.contentManager = contentManager;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    @Qualifier("processRequest")
    public void setCommand(Command command) {
        this.command = command;
    }

    @Autowired
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }

    @Autowired
    public void setScheduledTaskService(ScheduledTaskService scheduledTaskService) {
        this.scheduledTaskService = scheduledTaskService;
    }
}
