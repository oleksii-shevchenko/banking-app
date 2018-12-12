package ua.training.model.service.account;

import ua.training.model.entity.CreditAccount;
import ua.training.model.entity.DepositAccount;

import java.util.HashMap;
import java.util.Map;

public class AccountServiceFactory {
    private static Map<String, AccountService> services;

    static {
        services = new HashMap<>();
        services.put(CreditAccount.class.getSimpleName(), new CreditAccountService());
        services.put(DepositAccount.class.getSimpleName(), new DepositAccountService());
    }

    public static AccountService getService(String name) {
        return services.get(name);
    }

    public static void registerService(String name, AccountService service) {
        services.putIfAbsent(name, service);
    }
}
