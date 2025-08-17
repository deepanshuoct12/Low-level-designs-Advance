package org.dynamik.dao;

import java.util.List;

public interface IBaseDao <Entity, Id> {
    Entity findById(Id id);
    Entity save(Entity entity);
    void delete(Entity entity);
    void deleteById(Id id);
    Entity update(Entity entity);
    List<Entity> findAll();
}
