package ua.training.controller.servlet;

import ua.training.controller.commands.Command;
import ua.training.controller.util.CommandUtil;
import ua.training.controller.util.managers.PathManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Implementation of Front Servlet patter. It handles request to api and manage it.
 * @author Oleksii Shevchenko
 */
@WebServlet(name = "FrontServlet", urlPatterns = {"/api/*"})
public class FrontServlet extends HttpServlet {
    private Map<String, Command> commands;
    private PathManager pathManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void init() {
        commands = (Map<String, Command>) getServletContext().getAttribute("commands");
        pathManager = (PathManager) getServletContext().getAttribute("pathManager");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = new CommandUtil().extractCommand(request);
        String page = commands.getOrDefault(command, (r) -> "redirect:" + pathManager.getPath("path.error")).execute(request);

        if (page.contains("redirect:")) {
            response.sendRedirect(request.getContextPath() + page.replace("redirect:", ""));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
