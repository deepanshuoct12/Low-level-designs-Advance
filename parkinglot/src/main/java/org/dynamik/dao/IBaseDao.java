package org.dynamik.dao;

import java.util.List;

public interface IBaseDao <Entity, String>{
    Entity save(Entity entity);
    Entity findById(String id);
    void deleteById(String id);
    void update(Entity entity);
    List<Entity> getAll();
}
