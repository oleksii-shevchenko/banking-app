package ua.training.model.service;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.entity.Currency;
import ua.training.model.service.util.RequestUtil;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CurrencyExchangeService {
    private static Logger logger = LogManager.getLogger(CurrencyExchangeService.class);

    private static ReadWriteLock readWriteLock;
    private static volatile LocalDateTime lastUpdate;
    private static long validationTime;

    private static Map<Currency, BigDecimal> exchangeRates;
    private static Currency base;

    public void init() {
        ResourceBundle config = ResourceBundle.getBundle("fixer_io");

        readWriteLock = new ReentrantReadWriteLock();
        exchangeRates = new ConcurrentHashMap<>();

        lastUpdate = LocalDateTime.now();
        base = Currency.valueOf(config.getString("fixer.api.base"));
        validationTime = Long.valueOf(config.getString("fixer.api.valid"));

        updateRates();
    }

    public Currency getBase() {
        return base;
    }

    public BigDecimal exchangeRate(Currency from, Currency to) {
        return BigDecimal.ONE;

        //todo activate
        /*readWriteLock.readLock().lock();
        try {
            RequestUtil util = new RequestUtil();

            if (util.isRatesNotValid(lastUpdate, validationTime)) {
                readWriteLock.readLock().unlock();
                readWriteLock.writeLock().lock();
                if (util.isRatesNotValid(lastUpdate, validationTime)) {
                    updateRates();
                    lastUpdate = LocalDateTime.now();
                }
                readWriteLock.writeLock().unlock();
                readWriteLock.readLock().lock();
            }

            return getRate(from, to);
        } finally {
            readWriteLock.readLock().unlock();
        }*/
    }

    private BigDecimal getRate(Currency from, Currency to) {
        if (from.equals(base)) {
            return exchangeRates.get(to);
        } else {
            return exchangeRates.get(to).divide(exchangeRates.get(from), MathContext.DECIMAL128);
        }
    }

    private void updateRates() {
        ResourceBundle config = ResourceBundle.getBundle("fixer_io");
        RequestUtil util = new RequestUtil();

        String endPoint = config.getString("fixer.api.end_point");
        String key = config.getString("fixer.api.key");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(new HttpGet(util.buildRequestUri(endPoint, key)));
             JsonReader reader = new Gson().newJsonReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8))) {
            util.updateFromJson(reader, exchangeRates);
        } catch (Exception exception) {
            logger.error(exception);
        }
    }
}
