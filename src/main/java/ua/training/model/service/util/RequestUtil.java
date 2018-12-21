package ua.training.model.service.util;

import com.google.gson.stream.JsonReader;
import org.apache.http.client.utils.URIBuilder;
import ua.training.model.entity.Currency;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestUtil {
    public URI buildRequestUri(String endPoint, String key) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(endPoint);

        uriBuilder.setParameter("access_key", key);

        String targets = Arrays.stream(Currency.values()).
                map(Currency::name).
                collect(Collectors.joining(","));

        uriBuilder.setParameter("symbols", targets);
        return uriBuilder.build();
    }

    public String readJson(InputStream stream) throws IOException {
        return null;
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
        return ChronoUnit.HOURS.between(lastUpdate, LocalDateTime.now()) > validationPeriod;
    }
}
