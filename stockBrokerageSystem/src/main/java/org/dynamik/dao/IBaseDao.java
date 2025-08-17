package org.dynamik.dao;

import java.util.List;

public interface IBaseDao <Entity, Id> {
    Entity save(Entity entity);
    Entity getById(Id id);
    void update(Entity entity);
    void delete(Id id);
    List<Entity> getAll();
}
