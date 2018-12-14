package ua.training.model.service;

import ua.training.model.entity.Currency;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class CurrencyExchangeService {
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private static Map<String, BigDecimal> exchangeRates;

    public void init() {
        exchangeRates = new HashMap<>();
    }

    public BigDecimal exchangeRate(Currency from, Currency to) {
        return null;
    }


}
