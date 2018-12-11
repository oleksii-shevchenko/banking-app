package ua.training.model.service;

import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.DepositAccount;
import ua.training.model.entity.Transaction;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class ScheduledUpdateService {
    private static final int THREADS_NUMBER = 4;

    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREADS_NUMBER);
    private static Map<String, Function<Account, Transaction>> updaters;

    static {
        updaters = new HashMap<>();
        updaters.put(CreditUpdater.class.getSimpleName(), new CreditUpdater());
        updaters.put(DepositAccount.class.getSimpleName(), new DepositUpdater());
    }

    public static void registerUpdater(String key, Function<Account, Transaction> updater) {
        updaters.putIfAbsent(key, updater);
    }

    public void registerTask(String updaterKey, Long targetedAccount, LocalDate date, DaoFactory factory) {
        executorService.schedule(() -> factory.getTransactionDao().makeUpdate(targetedAccount, updaters.get(updaterKey)),
                computeDelay(date), TimeUnit.DAYS);
    }

    private long computeDelay(LocalDate endDate) {
        return Duration.between(LocalDate.now(), endDate).toDays();
    }
}
