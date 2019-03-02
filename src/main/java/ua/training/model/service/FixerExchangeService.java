package ua.training.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.training.model.entity.Currency;
import ua.training.model.service.util.FixerUtil;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * This service provides currency exchange rates.
 * @author Oleksii Shevchenko
 */
@Service
public class FixerExchangeService implements CurrencyExchangeService {
    private static Logger logger = LogManager.getLogger(FixerExchangeService.class);

    private FixerUtil fixerUtil;
    private ResourceBundle bundle;

    private ReadWriteLock lock;

    private long validationTime;
    private LocalDateTime lastUpdate;

    private Map<Currency, BigDecimal> exchangeRates;
    private Currency base;

    @Autowired
    public void setFixerUtil(FixerUtil fixerUtil) {
        this.fixerUtil = fixerUtil;
    }

    @Autowired
    @Qualifier("lock")
    public void setLock(ReadWriteLock lock) {
        this.lock = lock;
    }

    @Autowired
    @Qualifier("fixerConfig")
    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    @PostConstruct
    public void init() {
        lastUpdate = LocalDateTime.now();
        base = Currency.valueOf(bundle.getString("fixer.api.base"));
        validationTime = Long.valueOf(bundle.getString("fixer.api.valid"));

        exchangeRates = fixerUtil.getRatesOrDefault(base);
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
        if (fixerUtil.isRatesNotValid(lastUpdate, validationTime)) {
            lock.readLock().unlock();
            try {
                lock.writeLock().lock();
                if (fixerUtil.isRatesNotValid(lastUpdate, validationTime)) {
                    exchangeRates = fixerUtil.makeRequest(fixerUtil.getRequestUri());
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
