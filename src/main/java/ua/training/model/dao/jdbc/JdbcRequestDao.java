package ua.training.model.dao.jdbc;

import ua.training.model.dao.RequestDao;
import ua.training.model.entity.Request;

import java.util.List;

public class JdbcRequestDao implements RequestDao {
    @Override
    public List<Request> getAll() {
        return null;
    }

    @Override
    public List<Request> getAllByCompletion(boolean completion) {
        return null;
    }

    @Override
    public Request get(Long key) {
        return null;
    }

    @Override
    public List<Request> get(List<Long> keys) {
        return null;
    }

    @Override
    public Long insert(Request entity) {
        return null;
    }

    @Override
    public void update(Request entity) {

    }

    @Override
    public void remove(Request entity) {

    }
}
