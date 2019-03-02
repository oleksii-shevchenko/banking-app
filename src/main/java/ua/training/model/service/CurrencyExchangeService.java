package ua.training.model.service;

import ua.training.model.entity.Currency;

import java.math.BigDecimal;

public interface CurrencyExchangeService {
    Currency getBase();
    BigDecimal exchangeRate(Currency from, Currency to);
}
