package ua.training.model.dao;

import ua.training.model.entity.Request;

import java.util.List;

public interface RequestDao extends Dao<Long, Request> {
    Request processRequest(Long requestId);

    List<Request> getAllByCompletion(boolean completion);
}
