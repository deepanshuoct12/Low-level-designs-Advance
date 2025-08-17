package org.dynamik.service;

import java.util.List;

public interface BaseService<T, ID> {
    T save(T entity);
    T findById(ID id);
    List<T> findAll();
    void delete(ID id);
}
