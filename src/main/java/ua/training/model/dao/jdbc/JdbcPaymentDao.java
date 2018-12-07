package ua.training.model.dao.jdbc;

import ua.training.model.dao.PaymentDao;
import ua.training.model.entity.Payment;

import java.util.List;

public class JdbcPaymentDao implements PaymentDao {
    @Override
    public List<Payment> getAllPaymentsByRequester(Long accountId) {
        return null;
    }

    @Override
    public List<Payment> getAllPaymentsByPayer(Long accountId) {
        return null;
    }

    @Override
    public Payment get(Long key) {
        return null;
    }

    @Override
    public List<Payment> get(List<Long> keys) {
        return null;
    }

    @Override
    public void insert(Payment entity) {

    }

    @Override
    public void update(Payment entity) {

    }

    @Override
    public void remove(Payment entity) {

    }
}
