package ua.training.model.dao;

import ua.training.model.entity.Invoice;

import java.util.List;

public interface InvoiceDao extends Dao<Long, Invoice> {
    List<Invoice> getInvoicesByRequester(Long accountId);
    List<Invoice> getInvoicesByPayer(Long accountId);
}
