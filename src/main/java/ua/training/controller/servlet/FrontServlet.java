package ua.training.controller.servlet;

import ua.training.controller.command.*;
import ua.training.controller.util.managers.PathManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "FrontServlet", urlPatterns = {"/api/*"})
public class FrontServlet extends HttpServlet {
    private Map<String, Command> commands;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    public void init() {
        commands = new HashMap<>();
        commands.put("signIn", new SignInCommand());
        commands.put("signUp", new SignUpCommand());
        commands.put("signOut", new SignOutCommand());
        commands.put("changeLanguage", new ChangeLanguageCommand());
        commands.put("currencyRate", new CurrencyRateCommand());
        commands.put("openAccount", new OpenAccountCommand());
        commands.put("request", new RequestCommand());
        commands.put("closeAccount", new CloseAccountCommand());
        commands.put("showAccounts", new ShowAccountsCommand());
        commands.put("infoAccount", new InfoAccountCommand());
        commands.put("infoTransaction", new InfoTransactionCommand());
        commands.put("updateUser", new UpdateUserCommand());
        commands.put("workspace", new WorkspaceCommand());
        commands.put("createInvoice", new CreateInvoiceCommand());
        commands.put("infoInvoice", new InfoInvoiceCommand());
        commands.put("completeInvoice", new CompleteInvoiceCommand());
        commands.put("showRequests", new ShowRequestsCommand());
        commands.put("processRequest", new ProcessRequestCommand());
        commands.put("replenishAccount", new ReplenishAccountCommand());
        commands.put("showInvoices", new ShowInvoicesCommand());

        startServices();
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getRequestURI().replaceAll(".*/api/", "");
        String page = commands.getOrDefault(command, (r) -> PathManager.getPath("path.error")).execute(request);



        if (page.contains("redirect:")) {
            response.sendRedirect(request.getContextPath() + page.replace("redirect:", ""));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    private void startServices() {
        //new ScheduledUpdateService().init(JdbcDaoFactory.getInstance());
        //new CurrencyExchangeService().init();
    }
}
