package ua.training.model.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.model.exception.NonActiveAccountException;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.service.FixerExchangeService;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FixerExchangeService.class, CreditAccount.class})
@PowerMockIgnore({"org.apache.logging.log4j.*", "javax.xml.parsers.*", "com.sun.org.apache.xerces.internal.jaxp.*"})
public class CreditAccountTest {
    private CreditAccount account;

    @Mock
    private FixerExchangeService service;

    @Before
    public void buildCreditAccount() {
        account = CreditAccount.getBuilder()
                .setBalance(BigDecimal.ZERO)
                .setCurrency(Currency.UAH)
                .setStatus(Account.Status.ACTIVE)
                .setCreditLimit(BigDecimal.valueOf(100.0))
                .setCreditRate(BigDecimal.valueOf(0.25))
                .build();
    }


    @Test(expected = NonActiveAccountException.class)
    public void givenNonActiveAccountWhenTryWithdrawMoneyThenThrowException() throws NonActiveAccountException{
        account.setStatus(Account.Status.CLOSED);
        account.withdrawFromAccount(null);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void givenCreditAccountWhenTryWithdrawMoreThenCreditLimitThrowException() throws Exception{
        when(service.exchangeRate(Currency.UAH, Currency.UAH)).thenReturn(BigDecimal.ONE);

        PowerMockito.whenNew(FixerExchangeService.class).withNoArguments().thenReturn(service);

        Transaction transaction = Transaction.getBuilder()
                .setAmount(BigDecimal.valueOf(200.0))
                .setCurrency(Currency.UAH)
                .build();

        account.withdrawFromAccount(transaction);
    }

    @Test
    public void givenCreditAccountWhenReplenishAccountThenGetCorrectBalance() throws Exception {
        when(service.exchangeRate(Currency.UAH, Currency.UAH)).thenReturn(BigDecimal.ONE);

        PowerMockito.whenNew(FixerExchangeService.class).withNoArguments().thenReturn(service);

        Transaction transaction = Transaction.getBuilder()
                .setAmount(BigDecimal.valueOf(225.15))
                .setCurrency(Currency.UAH)
                .build();

        account.setBalance(BigDecimal.valueOf(100.0));
        account.replenishAccount(transaction);

        assertEquals(BigDecimal.valueOf(325.15), account.getBalance());
    }
}
