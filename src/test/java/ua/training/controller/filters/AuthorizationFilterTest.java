package ua.training.controller.filters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import ua.training.controller.util.CommandUtil;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(CommandUtil.class)
public class AuthorizationFilterTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private AuthorizationFilter filter;

    @Before
    public void initRequest() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(eq("role"))).thenReturn("USER");

        filter = new AuthorizationFilter();
        filter.init(null);
    }

    @Test
    public void givenAuthorizedAccessWhenFilteringThenCallDoFilter() throws Exception {
        when(request.getRequestURI()).thenReturn("/banking/api/workspace");

        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void givenWrongCommandWhenFilteringThenSentRedirect() throws Exception {
        when(request.getRequestURI()).thenReturn("/banking/api/wrong");

        filter.doFilter(request, response, filterChain);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void givenNonAuthorizedAccessWhenFilteringThenSentRedirect() throws Exception {
        when(request.getRequestURI()).thenReturn("/banking/api/openAccount");

        filter.doFilter(request, response, filterChain);

        verify(response).sendRedirect(anyString());
    }
}
