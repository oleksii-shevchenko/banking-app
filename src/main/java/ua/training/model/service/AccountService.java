package ua.training.model.service;

import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.dto.InvoiceDto;
import ua.training.model.entity.Account;

import java.util.List;

public class AccountService {
    private DaoFactory factory;

    public AccountService(DaoFactory factory) {
        this.factory = factory;
    }

    public List<Account> getAccounts(Long userId) {
        return factory.getAccountDao().getUserAccounts(userId);
    }

    public Account getAccount(Long accountId) {
        return factory.getAccountDao().get(accountId);
    }

    public InvoiceDto getInvoices(Long accountId) {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setInvoicesAsPayer(factory.getInvoiceDao().getInvoicesByPayer(accountId));
        invoiceDto.setInvoicesAsRequester(factory.getInvoiceDao().getInvoicesByRequester(accountId));
        return invoiceDto;
    }
}
