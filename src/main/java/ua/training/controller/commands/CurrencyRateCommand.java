package ua.training.controller.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.entity.Currency;
import ua.training.model.service.CurrencyExchangeService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller("currencyRare")
public class CurrencyRateCommand implements Command {
    private CurrencyExchangeService exchangeService;

    private PathManager pathManager;

    @Override
    public String execute(HttpServletRequest request) {
        Currency base;
        if (Objects.isNull(request.getParameter("base"))) {
            base = exchangeService.getBase();
        } else {
            base = Currency.valueOf(request.getParameter("base"));
        }

        request.setAttribute("base", base);
        request.setAttribute("rates", generateRatesMap(base, exchangeService));

        return pathManager.getPath("path.rates");
    }

    private Map<Currency, BigDecimal> generateRatesMap(Currency base, CurrencyExchangeService service) {
        Map<Currency, BigDecimal> rates = new HashMap<>();

        for (Currency currency : Currency.values()) {
            rates.put(currency, service.exchangeRate(base, currency));
        }

        return rates;
    }

    @Autowired
    @Qualifier("fixerExchangeService")
    public void setExchangeService(CurrencyExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @Autowired
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }
}
