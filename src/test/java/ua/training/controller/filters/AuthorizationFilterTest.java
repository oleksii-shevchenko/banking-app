package ua.training.controller.filters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import ua.training.controller.util.CommandUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(CommandUtil.class)
public class AuthorizationFilterTest {
    @Mock
    private static HttpServletRequest request;

    @Mock
    private static HttpSession session;

    @Mock
    private static HttpServletResponse response;

    @Before
    public void initRequest() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(eq("role"))).thenReturn("USER");
    }

    @Test
    public void givenAuthorizedAccessWhenFilteringThenCallDoFilter() throws Exception {
        when(request.getRequestURI()).thenReturn("/banking/api/workspace");

        FilterChain filterChain = mock(FilterChain.class);

        Filter filter = new AuthorizationFilter();
        filter.init(null);
        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}
