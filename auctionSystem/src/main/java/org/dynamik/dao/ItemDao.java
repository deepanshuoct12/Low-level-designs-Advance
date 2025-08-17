package org.dynamik.dao;

import org.dynamik.enums.ItemCategory;
import org.dynamik.model.Item;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemDao implements IBaseDao<Item, String> {
    private static final Map<String, Item> items = new ConcurrentHashMap<>();

    @Override
    public Item findById(String id) {
        return items.get(id);
    }

    @Override
    public Item save(Item item) {
        return items.put(item.getId(), item);
    }

    @Override
    public void delete(Item item) {
        items.remove(item.getId());
    }

    @Override
    public void deleteById(String id) {
        items.remove(id);
    }

    @Override
    public Item update(Item item) {
        return items.put(item.getId(), item);
    }

    @Override
    public List<Item> findAll() {
        return items.values().stream().toList();
    }

    public List<Item> findItemsByUserId(String userId) {
        return items.values().stream()
                .filter(item -> item.getUserId().equals(userId))
                .toList();
    }

    public List<Item> findItemsByCategory(ItemCategory category) {
        return items.values().stream()
                .filter(item -> item.getCategory().equals(category))
                .toList();
    }

    public List<Item> findItemsByName(String name) {
        return items.values().stream()
                .filter(item -> item.getName().equals(name))
                .toList();
    }
}
