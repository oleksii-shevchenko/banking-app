package ua.training.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.dto.InvoiceDto;
import ua.training.model.dto.PageDto;
import ua.training.model.entity.Account;
import ua.training.model.entity.Invoice;
import ua.training.model.entity.Request;
import ua.training.model.entity.Transaction;

import java.util.List;

/**
 * This service designed to compose functions to work with account instance.
 * @author Oleksii Shevchenko
 */
@Service
public class AccountService {
    private DaoFactory factory;

    @Autowired
    public AccountService(@Qualifier("jdbcDaoFactory") DaoFactory factory) {
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

    public void makeTransaction(Transaction transaction) {
        factory.getTransactionDao().makeTransaction(transaction);
    }

    public void createInvoice(Invoice invoice) {
        factory.getInvoiceDao().insert(invoice);
    }

    public Invoice getInvoice(Long invoiceId) {
        return factory.getInvoiceDao().get(invoiceId);
    }

    public void denyInvoice(Long invoiceId) {
        factory.getInvoiceDao().denyInvoice(invoiceId);
    }

    public void acceptInvoice(Long invoiceId) {
        factory.getInvoiceDao().acceptInvoice(invoiceId);
    }

    public PageDto<Transaction> getTransactionsPage(Long accountId, int itemsNumber, int page) {
        return factory.getTransactionDao().getPage(accountId, itemsNumber, page);
    }

    public PageDto<Request> getRequestsPage(int itemsNumber, int page) {
        return factory.getRequestDao().getPage(itemsNumber, page);
    }

    public Transaction getTransaction(Long transactionId) {
        return factory.getTransactionDao().get(transactionId);
    }

    public void accountForceClosing(Long accountId) {
        factory.getAccountDao().accountForceClosing(accountId);
    }
}
