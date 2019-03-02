package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.ValidationUtil;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.entity.Currency;
import ua.training.model.entity.Invoice;
import ua.training.model.entity.User;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * This command used to create invoices by users. Positive execution possible if and only if user is one of holders of
 * the invoice requester account. Required params: requester - id of requester account, payer - id of payer account,
 * amount - positive amount of money to pay, currency, description (optional).
 * @author Oleksii Shevchenko
 */
@Controller("createInvoice")
public class CreateInvoiceCommand implements Command {
    private static Logger logger = LogManager.getLogger(CreateInvoiceCommand.class);

    private UserService userService;
    private AccountService accountService;

    private ValidationUtil validationUtil;
    private PathManager pathManager;

    @Override
    public String execute(HttpServletRequest request) {
        User user = userService.get((Long) request.getSession().getAttribute("id"));

        request.setAttribute("accountIds", user.getAccounts());
        request.setAttribute("currencies", Currency.values());

        if (!validationUtil.makeValidation(request, List.of("requester", "payer", "amount", "currency"))) {
            logger.warn("User " + user.getId() + " inputs not valid data");

            return pathManager.getPath("path.create-invoice");
        }

        Invoice invoice = Invoice.getBuilder()
                .setRequester(Long.valueOf(request.getParameter("requester")))
                .setPayer(Long.valueOf(request.getParameter("payer")))
                .setStatus(Invoice.Status.PROCESSING)
                .setAmount(new BigDecimal(request.getParameter("amount")))
                .setCurrency(Currency.valueOf(request.getParameter("currency")))
                .setDescription(request.getParameter("description"))
                .build();

        if (!user.getAccounts().contains(invoice.getRequester())) {
            logger.warn("User " + user.getId() + "try to access account " + invoice.getRequester() + " without permissions");

            return "redirect:" + pathManager.getPath("path.error");
        }

        accountService.createInvoice(invoice);

        return "redirect:" + pathManager.getPath("path.completed");
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
}
