package ua.training.model.dao.mapper;

public interface Mapper<E, R> {
    E map(R resource);
}
