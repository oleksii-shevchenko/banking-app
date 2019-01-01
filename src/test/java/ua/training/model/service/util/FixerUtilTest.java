package ua.training.model.service.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LocalDateTime.class)
@PowerMockIgnore({"org.apache.logging.log4j.*", "javax.xml.parsers.*", "com.sun.org.apache.xerces.internal.jaxp.*"})
public class FixerUtilTest {
    private LocalDateTime now;

    @Before
    public void initNow() {
        now = LocalDateTime.parse("2019-01-01T13:30");
    }

    @Test
    public void givenValidDataTimeWhenCheckingIsNotValidThenGetFalse() {
        PowerMockito.mockStatic(LocalDateTime.class);

        when(LocalDateTime.now()).thenReturn(now);

        long validationTime = 6;
        LocalDateTime lastUpdate = now.minusHours(3);

        Assert.assertFalse(new FixerUtil().isRatesNotValid(lastUpdate, validationTime));
    }

    @Test
    public void givenLastUpdateTheSameAsNowWhenCheckingIsNotValidThenGetFalse() {
        PowerMockito.mockStatic(LocalDateTime.class);

        when(LocalDateTime.now()).thenReturn(now);

        long validationTime = 6;

        Assert.assertFalse(new FixerUtil().isRatesNotValid(now, validationTime));
    }

    @Test
    public void givenLastUpdateInLimitOfValidThenCheckingIsNotValidThenGetFalse() {
        PowerMockito.mockStatic(LocalDateTime.class);

        when(LocalDateTime.now()).thenReturn(now);

        long validationTime = 6;
        LocalDateTime lastUpdate = now.minusHours(6);

        Assert.assertFalse(new FixerUtil().isRatesNotValid(lastUpdate, validationTime));
    }

    @Test
    public void givenInvalidDataWhenCheckingIsNotValidThenGetTrue() {
        PowerMockito.mockStatic(LocalDateTime.class);

        when(LocalDateTime.now()).thenReturn(now);

        long validationTime = 6;
        LocalDateTime lastUpdate = now.minusHours(10);

        Assert.assertTrue(new FixerUtil().isRatesNotValid(lastUpdate, validationTime));
    }
}
