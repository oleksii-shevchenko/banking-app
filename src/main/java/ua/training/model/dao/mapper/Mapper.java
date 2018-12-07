package ua.training.model.dao.mapper;

public interface Mapper<T, R> {
    T map(R resource);
}
