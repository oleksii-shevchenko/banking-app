package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.ValidationUtil;
import ua.training.controller.util.managers.ContentManager;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.entity.Currency;
import ua.training.model.entity.Transaction;
import ua.training.model.entity.User;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Controller("makeTransaction")
public class MakeTransactionCommand implements Command {
    private static Logger logger = LogManager.getLogger(MakeTransactionCommand.class);

    private UserService userService;
    private AccountService accountService;

    private ValidationUtil validationUtil;
    private PathManager pathManager;
    private ContentManager contentManager;

    @Override
    public String execute(HttpServletRequest request) {
        User user = userService.get((Long) request.getSession().getAttribute("id"));

        request.setAttribute("accountIds", user.getAccounts());
        request.setAttribute("currencies", Currency.values());

        if (!validationUtil.makeValidation(request, List.of("sender", "receiver", "amount", "currency"))) {
            logger.warn("User " + user.getId() + " input not valid data");

            return pathManager.getPath("path.make-transaction");
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

            return "redirect:" + pathManager.getPath("path.error");
        }

        try {
            accountService.makeTransaction(transaction);
        } catch (NotEnoughMoneyException exception) {
            logger.warn(exception);

            contentManager.setLocalizedMessage(request, "notEnough", "content.message.not.enough.money");

            return pathManager.getPath("path.make-transaction");
        }

        return pathManager.getPath("path.completed");
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }

    @Autowired
    public void setContentManager(ContentManager contentManager) {
        this.contentManager = contentManager;
    }
}
