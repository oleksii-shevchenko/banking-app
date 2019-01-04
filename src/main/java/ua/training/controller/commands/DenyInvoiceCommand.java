package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Invoice;
import ua.training.model.entity.User;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Command used by user to mark the invoice as denied. Positive execution possible if and only if user is one of the
 * invoice payer account holders. Required params: masterAccount - invoice payer account id, invoiceId.
 * @author Oleksii Shevchenko
 */
public class DenyInvoiceCommand implements Command {
    private static Logger logger = LogManager.getLogger(DenyInvoiceCommand.class);

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

        new AccountService(JdbcDaoFactory.getInstance()).denyInvoice(invoiceId);

        logger.info("User " + user.getId() + " denied invoice " + invoice.getId());

        return "redirect:" + PathManager.getPath("path.completed");
    }
}
