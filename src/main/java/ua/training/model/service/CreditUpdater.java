package ua.training.model.service;

import ua.training.model.entity.Account;
import ua.training.model.entity.Transaction;

import java.util.function.Function;

public class CreditUpdater implements Function<Account, Transaction> {
    @Override
    public Transaction apply(Account account) {
        return null;
    }
}
