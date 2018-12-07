package ua.training.model.dao;

import java.util.List;

public interface Dao<K, E> {
    E get(K key);
    List<E> get(List<K> keys);
    K insert(E entity);
    void update(E entity);
    void remove(E entity);
}
