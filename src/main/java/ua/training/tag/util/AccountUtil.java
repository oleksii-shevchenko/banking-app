package ua.training.tag.util;

import ua.training.model.entity.Account;

import java.math.RoundingMode;

public class AccountUtil {
    public String getCurrencySign(Account account) {
        String currency = account.getCurrency().name().toLowerCase();
        return FormatManager.getFormat("content.tag.currency." + currency);
    }

    public String getFormattedBalance(Account account) {
        return account.getBalance().setScale(2, RoundingMode.FLOOR).toString();
    }
}
