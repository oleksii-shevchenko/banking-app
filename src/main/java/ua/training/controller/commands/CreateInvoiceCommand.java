package ua.training.controller.commands;

import ua.training.controller.util.ValidationUtil;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Currency;
import ua.training.model.entity.Invoice;
import ua.training.model.entity.User;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

public class CreateInvoiceCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        User user = new UserService(JdbcDaoFactory.getInstance()).get((Long) request.getSession().getAttribute("id"));

        request.setAttribute("accountIds", user.getAccounts());
        request.setAttribute("currencies", Currency.values());

        ValidationUtil util = new ValidationUtil();
        if (!util.makeValidation(request, List.of("requester", "payer", "amount", "currency"))) {
            return PathManager.getPath("path.create-invoice");
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
            return "redirect:" + PathManager.getPath("path.error");
        }

        new AccountService(JdbcDaoFactory.getInstance()).createInvoice(invoice);

        return "redirect:" + PathManager.getPath("path.completed");
    }
}
