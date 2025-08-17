package org.dynamik.dao;

public interface BaseDao<T, ID> {
    T save(T entity);
    T findById(ID id);  // Returns null if not found
    void delete(T entity);
    void deleteById(ID id);
    boolean existsById(ID id);
}
