package ua.training.model.dao;

import ua.training.model.entity.Request;

import java.util.List;

/**
 * This is extension of template dao for {@link Request} entity.
 * @see Dao
 * @see Request
 * @author Oleksii Shevchenko
 */
public interface RequestDao extends Dao<Long, Request> {
    Request processRequest(Long requestId);

    List<Request> getAllByCompletion(boolean completion);
}
