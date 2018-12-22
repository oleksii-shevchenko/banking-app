package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.dto.InvoiceDto;
import ua.training.model.service.AccountService;

import javax.servlet.http.HttpServletRequest;

public class ShowInvoicesCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        AccountService service = new AccountService(JdbcDaoFactory.getInstance());

        Long accountId = Long.valueOf(request.getParameter("id"));
        Long userId = (Long) request.getSession().getAttribute("id");

        if (!service.getAccount(accountId).getHolders().contains(userId)) {
            return "redirect:" + PathManager.getPath("path.error");
        }

        InvoiceDto invoiceDto = service.getInvoices(accountId);

        request.setAttribute("payer", invoiceDto.getInvoicesAsPayer());
        request.setAttribute("requester", invoiceDto.getInvoicesAsRequester());

        return PathManager.getPath("path.all-invoices");
    }
}
