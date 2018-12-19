package ua.training.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class GuestFilter implements Filter {
    private static Logger logger = LogManager.getLogger(GuestFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (Objects.isNull(request.getSession(false))) {
            HttpSession session = request.getSession();

            session.setAttribute("role", User.Role.GUEST.name());
            session.setAttribute("lang", "en-US");

            logger.info("Create default session " + session.getId() + " for guest user");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}
