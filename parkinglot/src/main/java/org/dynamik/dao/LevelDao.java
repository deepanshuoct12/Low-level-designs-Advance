package org.dynamik.dao;

import org.dynamik.model.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LevelDao implements IBaseDao<Level, String> {
    private Map<String, Level> levels = new HashMap<>();

    @Override
    public Level save(Level level) {
        return levels.put(level.getId(), level);
    }

    @Override
    public Level findById(String id) {
        return levels.get(id);
    }

    @Override
    public void deleteById(String id) {
        levels.remove(id);
    }

    @Override
    public void update(Level level) {
        if (levels.get(level.getId()) != null) {
            levels.put(level.getId(), level);
        }
    }

    @Override
    public List<Level> getAll() {
        return levels.values().stream().collect(Collectors.toList());
    }

    public Level findByFloor(Long floor) {
        return levels.values().stream()
                .filter(level -> floor.equals(level.getFloor()))
                .findFirst()
                .orElse(null);
    }
}
