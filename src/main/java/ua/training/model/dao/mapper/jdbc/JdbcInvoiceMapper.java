package ua.training.model.dao.mapper.jdbc;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.Currency;
import ua.training.model.entity.Invoice;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcInvoiceMapper implements Mapper<Invoice, ResultSet> {
    @Override
    public Invoice map(ResultSet resultSet) throws SQLException {
            return Invoice.getBuilder()
                    .setId(resultSet.getLong("invoice_id"))
                    .setRequester(resultSet.getLong("requester_id"))
                    .setPayer(resultSet.getLong("payer_id"))
                    .setAmount(resultSet.getBigDecimal("invoice_amount"))
                    .setCurrency(Currency.valueOf(resultSet.getString("invoice_currency")))
                    .setStatus(Invoice.Status.valueOf(resultSet.getString("invoice_status")))
                    .setDescription(resultSet.getString("invoice_description"))
                    .setTransaction(resultSet.getLong("invoice_transaction"))
                    .build();
    }
}
