package ua.training.controller.commands;

import ua.training.controller.util.managers.ContentManager;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Invoice;
import ua.training.model.entity.User;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class CompleteInvoiceCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("masterAccount"));
        Long invoiceId = Long.valueOf(request.getParameter("id"));

        User user = new UserService(JdbcDaoFactory.getInstance()).get((Long) request.getSession().getAttribute("id"));

        Invoice invoice = new AccountService(JdbcDaoFactory.getInstance()).getInvoice(invoiceId);

        if (!user.getAccounts().contains(accountId) || !(accountId.equals(invoice.getPayer()) || accountId.equals(invoice.getRequester()))) {
            return "redirect:" + PathManager.getPath("path.error");
        }

        try {
            new AccountService(JdbcDaoFactory.getInstance()).acceptInvoice(invoiceId);
        } catch (NotEnoughMoneyException exception) {
            ContentManager.setLocalizedMessage(request, "notEnough", "content.info.invoice.not.enough");

            request.setAttribute("invoice", invoice);
            request.setAttribute("masterAccount", accountId);

            return PathManager.getPath("path.invoice");
        }

        return "redirect:" + PathManager.getPath("path.completed");
    }
}
