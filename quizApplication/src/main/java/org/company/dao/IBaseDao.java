package org.company.dao;

public interface IBaseDao<Entity, ID> {
    Entity get(ID id);
    void save(Entity entity);
    void deleteById(ID id);
    void update(Entity entity);
    void delete(Entity entity);
}
