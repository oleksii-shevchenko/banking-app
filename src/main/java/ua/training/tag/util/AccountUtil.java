package ua.training.tag.util;

import ua.training.model.entity.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AccountUtil {
    public String getCurrencySign(Currency currency) {
        return FormatManager.getFormat("content.tag.currency." + currency.name().toLowerCase());
    }

    public String getFormattedBalance(BigDecimal balance) {
        return balance.setScale(2, RoundingMode.FLOOR).toString();
    }
}
