package org.dynamik.service;

import org.dynamik.dao.ItemDao;
import org.dynamik.enums.Category;
import org.dynamik.model.Item;

import java.util.List;

public class ItemService {
    private ItemDao itemDao = new ItemDao();

    // 1. Add item
    public void addItem(Item item) {
        itemDao.save(item);
    }

    // 2. Remove item
    public void removeItem(String id) {
        itemDao.delete(id);
    }

    // 3. Get particular item
    public Item getItem(String id) {
        return itemDao.get(id);
    }

    public void saveItem(Item item) {
        itemDao.save(item);
    }

    public List<Item> getAllItems() {
        return itemDao.getAll();
    }

    public void updateItem(Item item) {
        itemDao.update(item);
    }

    public void deleteItem(String id) {
        itemDao.delete(id);
    }

    public List<Item> getItemsByCategory(Category category) {
        return itemDao.getAll().stream().filter(item -> item.getCategory().equals(category)).toList();
    }
}
