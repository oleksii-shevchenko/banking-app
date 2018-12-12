package ua.training.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entity.DepositAccount;
import ua.training.model.exception.CancelingTaskException;
import ua.training.model.service.account.DepositAccountService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledUpdateService {
    private static Logger logger = LogManager.getLogger(ScheduledUpdateService.class);

    private static final int THREADS_NUMBER = 4;

    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREADS_NUMBER);

    public void registerDeposit(DepositAccount depositAccount, DaoFactory factory) {
        executorService.scheduleWithFixedDelay(
                () -> {
                    try {
                        factory.getTransactionDao().makeTransaction(depositAccount.getId(), DepositAccountService::depositUpdate);
                    } catch (CancelingTaskException exception) {
                        logger.info(exception);
                        throw new RuntimeException();
                    } catch (RuntimeException exception) {
                        logger.info(exception);
                    }
                },
                depositAccount.getUpdatePeriod(),
                depositAccount.getUpdatePeriod(),
                TimeUnit.DAYS
        );
        //todo choose variant
        /*
        ScheduledFuture<?> depositServicing = executorService.scheduleWithFixedDelay(
                () -> factory.getTransactionDao().makeTransaction(depositAccount.getId(), DepositAccountService::depositUpdate),
                depositAccount.getUpdatePeriod(),
                depositAccount.getUpdatePeriod(),
                TimeUnit.DAYS
        );
        executorService.schedule(
                () -> depositServicing.cancel(false),
                computeDelay(depositAccount.getExpiresEnd()),
                TimeUnit.DAYS
        ); */
    }

    private long computeDelay(LocalDate endDate) {
        return Duration.between(LocalDate.now(), endDate).toDays();
    }
}
