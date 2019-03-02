package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.ContentManager;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Invoice;
import ua.training.model.entity.User;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * This command used by users to complete the invoices. Positive execution possible if and only if user one of the holders
 * of invoice payer account. Required params: masterAccount - invoice requester account, invoiceId - id of invoice to
 * complete.
 * @author Oleksii Shevcehnko
 */
@Controller("completeInvoice")
public class CompleteInvoiceCommand implements Command {
    private static Logger logger = LogManager.getLogger(CompleteInvoiceCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("masterAccount"));
        Long invoiceId = Long.valueOf(request.getParameter("invoiceId"));

        User user = new UserService(JdbcDaoFactory.getInstance()).get((Long) request.getSession().getAttribute("id"));

        Invoice invoice = new AccountService(JdbcDaoFactory.getInstance()).getInvoice(invoiceId);

        if (!user.getAccounts().contains(accountId) || !accountId.equals(invoice.getPayer())) {
            logger.warn("User " + user.getId() + "try to access account " + accountId + " without permissions");

            return "redirect:" + PathManager.getPath("path.error");
        }

        try {
            new AccountService(JdbcDaoFactory.getInstance()).acceptInvoice(invoiceId);
        } catch (NotEnoughMoneyException exception) {
            logger.warn(exception);

            ContentManager.setLocalizedMessage(request, "notEnough", "content.info.invoice.not.enough");

            request.setAttribute("invoice", invoice);
            request.setAttribute("masterAccount", accountId);

            return PathManager.getPath("path.invoice");
        }

        logger.info("User " + user.getId() + " accept invoice " + invoiceId);

        return "redirect:" + PathManager.getPath("path.completed");
    }
}
