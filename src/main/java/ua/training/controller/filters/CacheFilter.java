package ua.training.controller.filters;

import org.apache.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This filter set headers in response to disable caching mechanism to prevent unwanted behavior caused by 'aggressive'
 * caching mechanism of some browsers when use 'back arrow'.
 * @author Oleksii Shevchenko
 */
@WebFilter(displayName = "cacheFilter", urlPatterns = {"/*"})
public class CacheFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.addHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        response.addHeader(HttpHeaders.CACHE_CONTROL, "no-store");
        response.addHeader(HttpHeaders.CACHE_CONTROL, "max-age=0");

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}
