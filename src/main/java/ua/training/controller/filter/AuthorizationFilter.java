package ua.training.controller.filter;

import ua.training.controller.util.PathManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This filter implements authorization mechanism. If user has permissions to make such request, than the request will
 * be pass further, else the response will be sent to error page. If there is even no such commend, response will be
 * sent to error page to.
 */
@WebFilter(filterName = "authorization", urlPatterns = {"/api/*"})
public class AuthorizationFilter implements Filter {
    private Map<String, List<String>> permissions;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Todo add permissions
        permissions = new HashMap<>();

        permissions.put("guest", List.of("signIn",
                "signUp"));

        permissions.put("user", List.of("signOut"));

        permissions.put("admin", List.of("signOut"));
    }

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String command = request.getRequestURI().replaceAll(".*/api/", "");
        String role = (request.getSession(false) != null) ?
                (String) request.getSession().getAttribute("role") : "guest";

        if (permissions.get(role).contains(command)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect(request.getContextPath() + PathManager.getPath("path.error"));
        }
    }
}
