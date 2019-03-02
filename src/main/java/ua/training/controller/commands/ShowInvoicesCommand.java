package ua.training.controller.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.dto.InvoiceDto;
import ua.training.model.service.AccountService;

import javax.servlet.http.HttpServletRequest;

@Controller("showInvoices")
public class ShowInvoicesCommand implements Command {
    private AccountService accountService;
    private PathManager pathManager;

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        Long userId = (Long) request.getSession().getAttribute("id");

        if (!accountService.getAccount(accountId).getHolders().contains(userId)) {
            return "redirect:" + pathManager.getPath("path.error");
        }

        InvoiceDto invoiceDto = accountService.getInvoices(accountId);

        request.setAttribute("payer", invoiceDto.getInvoicesAsPayer());
        request.setAttribute("requester", invoiceDto.getInvoicesAsRequester());
        request.setAttribute("masterAccount", accountId);

        return pathManager.getPath("path.all-invoices");
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }
}
