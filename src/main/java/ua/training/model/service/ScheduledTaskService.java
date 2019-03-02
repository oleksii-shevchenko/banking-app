package ua.training.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.DepositAccount;
import ua.training.model.service.producers.DepositUpdater;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This service performs periodic or scheduled tasks in system.
 * @author Oleksii Shevchenko
 */
@Service
public class ScheduledTaskService {
    private final ScheduledExecutorService executorService;
    private final DaoFactory daoFactory;

    @Autowired
    public ScheduledTaskService(ScheduledExecutorService executorService, @Qualifier("jdbcDaoFactory") DaoFactory daoFactory) {
        this.executorService = executorService;
        this.daoFactory = daoFactory;
    }


    @PostConstruct
    public void init() {
        List<Account> accounts = daoFactory.getAccountDao().getActiveAccounts();

        for (Account account : accounts) {
            registerAccountBlocking(account, daoFactory);
            registerAccountClosing(account, daoFactory);

            if (account instanceof DepositAccount) {
                registerDeposit(account, daoFactory);
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
                getDepositUpdate().setAccountId(account.getId()),
                depositAccount.getUpdatePeriod(),
                depositAccount.getUpdatePeriod(),
                TimeUnit.DAYS
        );
    }

    @Lookup
    private DepositUpdater getDepositUpdate() {
        return null;
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
