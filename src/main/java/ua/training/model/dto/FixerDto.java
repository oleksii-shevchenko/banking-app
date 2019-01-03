package ua.training.model.dto;

import ua.training.model.entity.Currency;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * This DTO used in {@link ua.training.model.service.util.FixerUtil} for parsing JSON responses using GSON API.
 * @see ua.training.model.service.util.FixerUtil
 * @see ua.training.model.service.CurrencyExchangeService
 * @author Oleksii Shevchenko
 */
public class FixerDto {
    private boolean success;
    private long timestamp;
    private Currency base;
    private Date date;
    private Map<Currency, BigDecimal> rates;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Currency getBase() {
        return base;
    }

    public void setBase(Currency base) {
        this.base = base;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<Currency, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(Map<Currency, BigDecimal> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "success=" + success +
                ", timestamp=" + timestamp +
                ", base='" + base + '\'' +
                ", date=" + date +
                ", rates=" + rates +
                '}';
    }
}
