package ua.training.model.service;

import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entity.User;

public class UserService {
    private DaoFactory factory;

    public UserService(DaoFactory factory) {
        this.factory = factory;
    }

    public User get(Long id) {
        return factory.getUserDao().get(id);
    }
}
