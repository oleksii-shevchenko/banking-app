package ua.training.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ua.training.model.entity.Currency;
import ua.training.model.service.util.FixerUtil;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This service provides currency exchange rates.
 * @author Oleksii Shevchenko
 */
@Service
public class FixerExchangeService implements CurrencyExchangeService {
    private static Logger logger = LogManager.getLogger(FixerExchangeService.class);

    private static ReadWriteLock lock;
    private static volatile LocalDateTime lastUpdate;
    private static long validationTime;

    private static volatile Map<Currency, BigDecimal> exchangeRates;
    private static Currency base;

    /**
     * Method to staring service.
     */
    public void init() {
        ResourceBundle config = ResourceBundle.getBundle("fixer_io");

        lastUpdate = LocalDateTime.now();
        base = Currency.valueOf(config.getString("fixer.api.base"));
        validationTime = Long.valueOf(config.getString("fixer.api.valid"));

        lock = new ReentrantReadWriteLock();
        exchangeRates = new FixerUtil().getRatesOrDefault(base);
    }

    @Override
    public Currency getBase() {
        return base;
    }

    /**
     * Return exchange rate from one currency to another.
     * @param from Currency from witch exchanging.
     * @param to Currency to witch exchanging.
     * @return Exchange rate.
     */
    @Override
    public BigDecimal exchangeRate(Currency from, Currency to) {
        try {
            lock.readLock().lock();
            updateRates();
            return competeRate(from, to);
        } finally {
            lock.readLock().unlock();
        }
    }

    private BigDecimal competeRate(Currency from, Currency to) {
        if (from.equals(base)) {
            return exchangeRates.get(to);
        } else {
            return exchangeRates.get(to).divide(exchangeRates.get(from), MathContext.DECIMAL128);
        }
    }

    private void updateRates() {
        FixerUtil util = new FixerUtil();
        if (util.isRatesNotValid(lastUpdate, validationTime)) {
            lock.readLock().unlock();
            try {
                lock.writeLock().lock();
                if (util.isRatesNotValid(lastUpdate, validationTime)) {
                    exchangeRates = util.makeRequest(util.getRequestUri());
                    lastUpdate = LocalDateTime.now();
                }
            } catch (Exception exception) {
                lastUpdate = LocalDateTime.now();
                logger.error("Error while updating rates. Validation time of old rates is extended");
            } finally {
                lock.writeLock().unlock();
            }
            lock.readLock().lock();
        }
    }
}
