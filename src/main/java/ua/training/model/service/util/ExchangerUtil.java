package ua.training.model.service.util;

import com.google.gson.stream.JsonReader;
import org.apache.http.client.utils.URIBuilder;
import ua.training.model.entity.Currency;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

public class ExchangerUtil {
    public URI buildRequestUri(String endPoint, String key) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(endPoint);

        uriBuilder.setParameter("access_key", key);
        for (Currency currency : Currency.values()) {
            uriBuilder.setParameter("symbols", currency.name());
        }

        return uriBuilder.build();
    }

    public void updateFromJson(JsonReader reader, Map<Currency, BigDecimal> rates) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            if (!reader.nextName().equals("rates")) {
                reader.skipValue();
                continue;
            }
            reader.beginObject();
            while (reader.hasNext()) {
                Currency currency = Currency.valueOf(reader.nextName());
                BigDecimal rate = BigDecimal.valueOf(reader.nextDouble());
                rates.put(currency, rate);
            }
            reader.endObject();
        }
        reader.endObject();
    }

    public boolean isRatesNotValid(LocalDateTime lastUpdate, long validationPeriod) {
        return Duration.between(lastUpdate, LocalDateTime.now()).toHours() > validationPeriod;
    }
}
