package ua.training.model.dao;

import ua.training.model.entity.Invoice;

import java.util.List;


/**
 * This is extension of template dao for {@link Invoice} entity.
 * @see Dao
 * @see Invoice
 * @author Oleksii Shevchenko
 */
public interface InvoiceDao extends Dao<Long, Invoice> {
    List<Invoice> getInvoicesByRequester(Long accountId);
    List<Invoice> getInvoicesByPayer(Long accountId);

    void acceptInvoice(Long invoiceId);
    void denyInvoice(Long invoiceId);
}
