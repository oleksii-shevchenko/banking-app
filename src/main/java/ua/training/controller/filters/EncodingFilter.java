package ua.training.controller.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * This filter sets content encoding UTF-8 for all requests and responses.
 * @author Oleksii Shevchenko
 */
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("text/html");

        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
