package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.entity.Invoice;
import ua.training.model.entity.User;
import ua.training.model.service.AccountService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller("infoInvoice")
public class InfoInvoiceCommand implements Command {
    private static Logger logger = LogManager.getLogger(InfoInvoiceCommand.class);

    private UserService userService;
    private AccountService accountService;

    private PathManager pathManager;

    @Override
    public String execute(HttpServletRequest request) {
        Long invoiceId = Long.valueOf(request.getParameter("invoiceId"));
        Long accountId = Long.valueOf(request.getParameter("masterAccount"));

        User user = userService.get((Long) request.getSession().getAttribute("id"));
        Invoice invoice = accountService.getInvoice(invoiceId);

        if (!user.getAccounts().contains(accountId) || !(accountId.equals(invoice.getPayer()) || accountId.equals(invoice.getRequester()))) {
            logger.warn("User " + user.getId() + "try to access account " + accountId + " without permissions");

            return "redirect:" + pathManager.getPath("path.error");
        }

        request.setAttribute("invoice", invoice);
        request.setAttribute("masterAccount", accountId);

        return pathManager.getPath("path.invoice");
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
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }
}
