package ua.training.model.service.schedules;

import ua.training.model.dao.factory.DaoFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ScheduledDepositService {
    private static int THREADS_NUMBER = 2;
    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREADS_NUMBER);

    void registerTask(Long accountId, DaoFactory factory, LocalDate date) {

    }

    private long computeDelay(LocalDate date) {
        return Duration.between(LocalDate.now(), date).toDays();
    }
}
