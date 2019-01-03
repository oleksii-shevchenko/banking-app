package ua.training.model.dto;

import ua.training.model.entity.Invoice;

import java.util.List;

/**
 * This DTO contains to separate list of invoices. In one user is requester, in another user is payer.
 * @see Invoice
 * @author Oleksii Shevchenko
 */
public class InvoiceDto {
    private List<Invoice> invoicesAsPayer;
    private List<Invoice> invoicesAsRequester;

    public List<Invoice> getInvoicesAsPayer() {
        return invoicesAsPayer;
    }

    public void setInvoicesAsPayer(List<Invoice> invoicesAsPayer) {
        this.invoicesAsPayer = invoicesAsPayer;
    }

    public List<Invoice> getInvoicesAsRequester() {
        return invoicesAsRequester;
    }

    public void setInvoicesAsRequester(List<Invoice> invoicesAsRequester) {
        this.invoicesAsRequester = invoicesAsRequester;
    }
}
