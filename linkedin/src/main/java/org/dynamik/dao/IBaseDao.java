package org.dynamik.dao;

import java.util.List;

public interface IBaseDao <T, ID> {
    T save(T t);
    List<T> findAll();
    void delete(ID id);
    T findById(ID id);
}
