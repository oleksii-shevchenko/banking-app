package ua.training.model.service;

import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.DepositAccount;
import ua.training.model.service.producers.DepositUpdater;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This service performs periodic or scheduled tasks in system.
 * @author Oleksii Shevchenko
 */
public class ScheduledTaskService {
    private static final int THREADS_NUMBER = 4;

    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREADS_NUMBER);

    /**
     * Method to staring service.
     * @param factory Factory that creates dao implementations.
     */
    public void init(DaoFactory factory) {
        List<Account> accounts = factory.getAccountDao().getActiveAccounts();

        for (Account account : accounts) {
            registerAccountBlocking(account, factory);
            registerAccountClosing(account, factory);

            if (account instanceof DepositAccount) {
                registerDeposit(account, factory);
            }
        }
    }

    /**
     * Register deposit account for periodic update.
     * @param account Targeted deposit account.
     * @param factory Factory for creating dao.
     */
    public void registerDeposit(Account account, DaoFactory factory) {
        DepositAccount depositAccount = (DepositAccount) account;
        executorService.scheduleWithFixedDelay(
                new DepositUpdater(factory, account.getId()),
                depositAccount.getUpdatePeriod(),
                depositAccount.getUpdatePeriod(),
                TimeUnit.DAYS
        );
    }

    private void registerAccountBlocking(Account account, DaoFactory factory) {
        executorService.schedule(() -> factory.getAccountDao().blockAccount(account.getId()), computeDelay(account.getExpiresEnd()), TimeUnit.DAYS);
    }

    private void registerAccountClosing(Account account, DaoFactory factory) {
        executorService.schedule(() -> factory.getAccountDao().closeAccount(account.getId()), computeDelay(account.getExpiresEnd()) + 1, TimeUnit.DAYS);
    }

    private long computeDelay(LocalDate endDate) {
        return ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    }
}
