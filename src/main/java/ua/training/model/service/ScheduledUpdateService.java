package ua.training.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.AccountDao;
import ua.training.model.dao.TransactionDao;
import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entity.Account;
import ua.training.model.exception.CancelingTaskException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledUpdateService {
    private static Logger logger = LogManager.getLogger(ScheduledUpdateService.class);

    private static final int THREADS_NUMBER = 4;

    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREADS_NUMBER);

    public void init(DaoFactory factory) {
        List<Account> accounts = factory.getAccountDao().getActiveAccounts();

        for (Account account : accounts) {
            registerAccountTask(account, factory);
        }
    }

    public void registerAccountTask(Account account, DaoFactory factory) {
        registerPeriodicAccountUpdate(account, factory.getTransactionDao());
        registerAccountBlocking(account, factory.getAccountDao());
        registerAccountClosing(account, factory.getAccountDao());
    }

    private void registerPeriodicAccountUpdate(Account account, TransactionDao transactionDao) {
        executorService.scheduleWithFixedDelay(
                () -> {
                    try {
                        transactionDao.makePeriodicUpdate(account.getId());
                    } catch (CancelingTaskException exception) {
                        logger.info(exception);
                        throw new RuntimeException();
                    } catch (RuntimeException exception) {
                        logger.info(exception);
                    }
                },
                account.getUpdatePeriod(),
                account.getUpdatePeriod(),
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
        return Duration.between(LocalDate.now(), endDate).toDays();
    }
}
