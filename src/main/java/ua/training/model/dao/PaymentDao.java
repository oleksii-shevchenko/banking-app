package ua.training.model.dao;

import ua.training.model.entity.Payment;

import java.util.List;

public interface PaymentDao extends Dao<Long, Payment> {
    List<Payment> getAllPaymentsByRequester(Long accountId);
    List<Payment> getAllPaymentsByPayer(Long accountId);
}
