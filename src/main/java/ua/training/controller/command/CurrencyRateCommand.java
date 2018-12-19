package ua.training.controller.command;

import ua.training.controller.util.managers.PathManager;
import ua.training.model.entity.Currency;
import ua.training.model.service.CurrencyExchangeService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CurrencyRateCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        CurrencyExchangeService service = new CurrencyExchangeService();

        Currency base;
        if (Objects.isNull(request.getParameter("base"))) {
            base = service.getBase();
        } else {
            base = Currency.valueOf(request.getParameter("base"));
        }

        request.setAttribute("base", base);
        request.setAttribute("rates", generateRatesMap(base, service));

        return PathManager.getPath("path.rates");
    }

    private Map<Currency, BigDecimal> generateRatesMap(Currency base, CurrencyExchangeService service) {
        Map<Currency, BigDecimal> rates = new HashMap<>();

        for (Currency currency : Currency.values()) {
            rates.put(currency, service.exchangeRate(base, currency));
        }

        return rates;
    }
}
