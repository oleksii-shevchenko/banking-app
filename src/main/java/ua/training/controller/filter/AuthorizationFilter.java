package ua.training.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.PathManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * This filter implements authorization mechanism. If user has permissions to make such request, than the request will
 * be pass further, else the response will be sent to error page. If there is even no such commend, response will be
 * sent to error page to.
 */
public class AuthorizationFilter implements Filter {
    private static Logger logger = LogManager.getLogger(AuthorizationFilter.class);

    private static Map<String, List<String>> permissions;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Todo add permissions
        permissions = new ConcurrentHashMap<>();

        permissions.put("GUEST", List.of("signIn",
                "signUp",
                "changeLanguage"));

        permissions.put("USER", List.of("signOut",
                "changeLanguage"));

        permissions.put("ADMIN", List.of("signOut",
                "changeLanguage"));
    }

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String command = request.getRequestURI().replaceAll(".*/api/", "");

        logger.trace(request.getRequestURL());

        String role = (String) request.getSession().getAttribute("role");

        if (permissions.get(role).contains(command)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect(request.getContextPath() + PathManager.getPath("path.error"));
        }
    }
}
