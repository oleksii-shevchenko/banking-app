package ua.training.controller.filter;

import ua.training.controller.util.PathManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JspFilter implements Filter {
    private static Map<String, List<String>> permissions;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String jsp = request.getRequestURI().replaceAll(".*/jsp/", "");
        String role = (String) request.getSession().getAttribute("role");

        if (permissions.get(role).contains(jsp)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect(request.getContextPath() + PathManager.getPath("path.error"));
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        permissions = new ConcurrentHashMap<>();

        permissions.put("GUEST", List.of("sign_in.jsp",
                "sign_up.jsp"));

        permissions.put("USER", List.of());

        permissions.put("ADMIN", List.of());
    }

    @Override
    public void destroy() {}
}
