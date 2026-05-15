package org.dynamik.dao;

import org.dynamik.model.Item;
import org.dynamik.model.User;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ItemDao implements IBaseDao<Item, String>{
    private static ConcurrentHashMap<String, Item> items = new ConcurrentHashMap<>();


    @Override
    public void update(Item item) {
        items.put(item.getId(), item);
    }

    @Override
    public void delete(String id) {
       items.remove(id);
    }

    @Override
    public void save(Item item) {
      items.put(item.getId(), item);
    }

    @Override
    public Item get(String id) {
        return items.get(id);
    }

    @Override
    public List<Item> getAll() {
        return items.values().stream().toList();
    }
}
