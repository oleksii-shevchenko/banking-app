package ua.training.model.dao;

import ua.training.model.entity.Invoice;

import java.util.List;

public interface RequestDao extends Dao<Long, Invoice> {
    List<Invoice> getAll();
    List<Invoice> getAllByCompletion(boolean completion);
}
