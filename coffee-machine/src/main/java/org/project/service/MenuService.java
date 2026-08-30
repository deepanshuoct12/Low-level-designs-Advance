package org.project.service;

import org.project.model.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MenuService {
    private static ConcurrentHashMap<String, Menu> menus = new ConcurrentHashMap<>();

    public Menu getMenu(String menuId) {
        return menus.get(menuId);
    }

    public Menu createMenu(Menu menu) {
        menus.put(menu.getId(), menu);
        return menu;
    }

    public List<Menu> getAll() {
        return new ArrayList<>(menus.values());
    }

    public void update(Menu menu) {
        menus.put(menu.getId(), menu);
    }

    public void delete(String menuId) {
        menus.remove(menuId);
    }
}
