package ua.training.model.dto;

import ua.training.model.entity.Invoice;

import java.util.List;

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
