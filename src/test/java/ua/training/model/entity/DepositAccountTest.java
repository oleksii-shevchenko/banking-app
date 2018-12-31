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
import ua.training.model.service.CurrencyExchangeService;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CurrencyExchangeService.class, DepositAccount.class})
@PowerMockIgnore({"org.apache.logging.log4j.*", "javax.xml.parsers.*", "com.sun.org.apache.xerces.internal.jaxp.*"})
public class DepositAccountTest {
    private DepositAccount account;

    @Mock
    private CurrencyExchangeService service;

    @Before
    public void buildDepositAccount() {
        account = DepositAccount.getBuilder()
                .setBalance(BigDecimal.ZERO)
                .setCurrency(Currency.UAH)
                .setStatus(Account.Status.ACTIVE)
                .setDepositRate(BigDecimal.valueOf(0.2))
                .setUpdatePeriod(30)
                .build();
    }

    @Test(expected = NonActiveAccountException.class)
    public void givenNonActiveAccountWhenTryWithdrawMoneyThenThrowException() throws NonActiveAccountException{
        account.setStatus(Account.Status.CLOSED);
        account.withdrawFromAccount(null);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void givenAccountWithZeroBalanceWhenWithdrawMoneyThenThrowException() throws Exception {
        when(service.exchangeRate(Currency.UAH, Currency.UAH)).thenReturn(BigDecimal.ONE);

        PowerMockito.whenNew(CurrencyExchangeService.class).withNoArguments().thenReturn(service);

        Transaction transaction = Transaction.getBuilder()
                .setAmount(BigDecimal.valueOf(200.0))
                .setCurrency(Currency.UAH)
                .build();

        account.withdrawFromAccount(transaction);
    }
}
