package ua.training.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.controller.util.CommandUtil;

import javax.servlet.http.HttpServletRequest;

@RunWith(MockitoJUnitRunner.class)
public class CommandUtilTest {
    @Mock
    private HttpServletRequest request;

    @Test
    public void givenRightCommandWhenExtractFromUriThenGetCommand() {
        Mockito.when(request.getRequestURI()).thenReturn("/banking/api/command");

        Assert.assertEquals("command", new CommandUtil().extractCommand(request));
    }
}
