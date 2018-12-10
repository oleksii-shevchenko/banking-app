package ua.training.model.dao;

/**
 * This is general template for data access object that includes only simple, general crud operations.
 * @param <K> Data type that are used as primary key for identifying entities.
 * @param <E> Entity that are served by this dao
 * @author Oleksii Shevchenko
 */
public interface Dao<K, E> {
    E get(K key);
    K insert(E entity);
    void update(E entity);
    void remove(E entity);
}
