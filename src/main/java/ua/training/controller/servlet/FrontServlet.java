package ua.training.controller.servlet;

import ua.training.controller.command.Command;
import ua.training.controller.command.SignInCommand;
import ua.training.controller.command.SignOutCommand;
import ua.training.controller.command.SignUpCommand;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.service.CurrencyExchangeService;
import ua.training.model.service.ScheduledUpdateService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
        getServletContext().setAttribute("signedInUsers", new ArrayList<Long>());

        commands = new HashMap<>();
        commands.put("signIn", new SignInCommand());
        commands.put("signUp", new SignUpCommand());
        commands.put("signOut", new SignOutCommand());
        //Todo add commands

        startServices();
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getRequestURI().replaceAll(".*/api/", "");
        String page = commands.get(command).execute(request);

        if (page.contains("redirect:")) {
            response.sendRedirect(request.getContextPath() + page.replace("redirect:", ""));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    private void startServices() {
        new ScheduledUpdateService().init(JdbcDaoFactory.getInstance());
        new CurrencyExchangeService().init();
    }
}
