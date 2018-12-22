package ua.training.model.service;

import ua.training.model.dao.AccountDao;
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

public class ScheduledTaskService {
    private static final int THREADS_NUMBER = 4;

    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREADS_NUMBER);

    public void init(DaoFactory factory) {
        List<Account> accounts = factory.getAccountDao().getActiveAccounts();

        for (Account account : accounts) {
            registerAccountBlocking(account, factory.getAccountDao());
            registerAccountClosing(account, factory.getAccountDao());

            if (account instanceof DepositAccount) {
                registerDeposit(account, factory);
            }
        }
    }

    private void registerDeposit(Account account, DaoFactory factory) {
        DepositAccount depositAccount = (DepositAccount) account;
        executorService.scheduleWithFixedDelay(
                new DepositUpdater(factory, account.getId()),
                depositAccount.getUpdatePeriod(),
                depositAccount.getUpdatePeriod(),
                TimeUnit.DAYS
        );
    }

    private void registerAccountBlocking(Account account, AccountDao accountDao) {
        executorService.schedule(() -> accountDao.blockAccount(account.getId()), computeDelay(account.getExpiresEnd()), TimeUnit.DAYS);
    }

    private void registerAccountClosing(Account account, AccountDao accountDao) {
        executorService.schedule(() -> accountDao.closeAccount(account.getId()), computeDelay(account.getExpiresEnd()) + 1, TimeUnit.DAYS);
    }

    private long computeDelay(LocalDate endDate) {
        return ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    }
}
