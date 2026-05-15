package org.dynamik.dao;

import java.util.List;

public interface IBaseDao <T,ID>{
    void update(T t);
    void delete(ID id);
    void save(T t);
    T get(ID id);
    List<T> getAll();
}
