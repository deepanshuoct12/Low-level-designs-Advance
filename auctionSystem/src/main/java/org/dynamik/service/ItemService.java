package org.dynamik.service;

import org.dynamik.dao.ItemDao;
import org.dynamik.enums.ItemCategory;
import org.dynamik.model.Item;

import java.util.List;

public class ItemService {
    private ItemDao itemDao;

    public ItemService() {
        itemDao = new ItemDao();
    }

    public void addItem(Item item) {
        itemDao.save(item);
    }

    public void updateItem(Item item) {
        itemDao.update(item);
    }

    public void removeItem(Item item) {
        itemDao.delete(item);
    }

    public void removeItemById(String id) {
        itemDao.deleteById(id);
    }

    public Item findItemById(String id) {
        return itemDao.findById(id);
    }

    public List<Item> findAllItems() {
        return itemDao.findAll();
    }

    public List<Item> findItemsByUserId(String userId) {
        return itemDao.findItemsByUserId(userId);
    }

    public List<Item> findItemsByCategory(ItemCategory category) {
        return itemDao.findItemsByCategory(category);
    }

    public List<Item> findItemsByName(String name) {
        return itemDao.findItemsByName(name);
    }
}
