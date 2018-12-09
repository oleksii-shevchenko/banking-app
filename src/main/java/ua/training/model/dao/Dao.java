package ua.training.model.dao;

public interface Dao<K, E> {
    E get(K key);
    K insert(E entity);
    void update(E entity);
    void remove(E entity);
}
