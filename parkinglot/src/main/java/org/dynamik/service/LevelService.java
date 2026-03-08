package org.dynamik.service;

import org.dynamik.dao.LevelDao;
import org.dynamik.model.Level;

import java.util.List;

public class LevelService {
    private final LevelDao levelDao;

    public LevelService() {
        this.levelDao = new LevelDao();
    }

    public Level save(Level level) {
        return levelDao.save(level);
    }

    public Level findById(String id) {
        return levelDao.findById(id);
    }

    public void deleteById(String id) {
        levelDao.deleteById(id);
    }

    public void update(Level level) {
        levelDao.update(level);
    }

    public List<Level> getAll() {
        return levelDao.getAll();
    }

    public Level findByFloor(Long floor) {
        return levelDao.findByFloor(floor);
    }
}
