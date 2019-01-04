package ua.training.controller.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * This web listener used for marking user as signed out when session automatically close.
 * @author Oleksii Shevchenko
 */
@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {}

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        se.getSession().getServletContext().removeAttribute((String) se.getSession().getAttribute("login"));
    }
}
