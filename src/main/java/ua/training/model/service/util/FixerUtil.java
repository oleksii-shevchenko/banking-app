package ua.training.model.service.util;

import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dto.FixerDto;
import ua.training.model.entity.Currency;

import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * This util used when interaction with fixer service api.
 * @see ua.training.model.service.CurrencyExchangeService
 * @author Oleksii Shevchenko
 */
public class FixerUtil {
    private static Logger logger = LogManager.getLogger(FixerUtil.class);

    private static ResourceBundle config = ResourceBundle.getBundle("fixer_io");

    /**
     * This method create uri for making request to fixer service.
     * @return Request URI
     */
    public URI getRequestUri() throws URISyntaxException {
        return new URIBuilder(config.getString("fixer.api.end_point"))
                .setParameter("access_key", config.getString("fixer.api.key"))
                .setParameter("symbols", buildSymbols())
                .setCharset(StandardCharsets.UTF_8)
                .build();
    }

    /**
     * Method makes http request to fixer service and return rates extracted for JSON response.
     * @param uri Request URI.
     * @return Currencies rates.
     * @throws Exception Thrown if the request is no success.
     */
    public Map<Currency, BigDecimal> makeRequest(URI uri) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(new HttpGet(uri));
             Reader reader = new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8)) {

            FixerDto fixerDto = new Gson().fromJson(reader, FixerDto.class);

            if (fixerDto.isSuccess()) {
                return fixerDto.getRates();
            } else {
                throw new Exception();
            }
        } catch (Exception exception) {
            logger.error(exception);
            throw new Exception(exception);
        }
    }

    /**
     * Checks is the data is still valid for last update.
     * @param lastUpdate Time of last update.
     * @param validationPeriod Time for witch data stays valid.
     * @return Is data valid.
     */
    public boolean isRatesNotValid(LocalDateTime lastUpdate, long validationPeriod) {
        return ChronoUnit.HOURS.between(lastUpdate, LocalDateTime.now()) > validationPeriod;
    }

    /**
     * Method used for getting default currencies rates in case the first update request is not success.
     * @param base The base currency when exchanging.
     * @return Default currencies rates.
     */
    public Map<Currency, BigDecimal> getRatesOrDefault(Currency base) {
        try {
            return makeRequest(getRequestUri());
        } catch (Exception exception) {
            logger.error(exception);
            return getDefaultRates(base);
        }
    }

    private Map<Currency, BigDecimal> getDefaultRates(Currency base) {
        Map<Currency, BigDecimal> rates = new HashMap<>();
        for (Currency target : Currency.values()) {
            rates.put(target, getDefaultRate(base, target));
        }
        return rates;
    }

    private String buildSymbols() {
        return Arrays.stream(Currency.values())
                .map(Currency::name)
                .collect(Collectors.joining(","));
    }

    private BigDecimal getDefaultRate(Currency base, Currency target) {
        String key = String.format("fixer.rate.%s.%s", base.name().toLowerCase(), target.name().toLowerCase());
        return new BigDecimal(config.getString(key));
    }
}
