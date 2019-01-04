package ua.training.controller.commands;

import javax.servlet.http.HttpServletRequest;

/**
 * The general interface of all commands; gets as argument user http request.
 * @see ua.training.controller.servlet.FrontServlet
 * @author Oleksii Shevchenko
 */
public interface Command {
    String execute(HttpServletRequest request);
}
