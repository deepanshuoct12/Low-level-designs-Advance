package org.project.service;

import org.project.model.Statistics;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

public class StatisticsService {
    private static final ConcurrentHashMap<Long, Statistics> statisticsStorage = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public Statistics create(Statistics statistics) {
        if (statistics.getId() == null) {
            statistics.setId(idCounter++);
        }
        statistics.setCreatedAt(System.currentTimeMillis());
        statistics.setUpdatedAt(System.currentTimeMillis());
        statisticsStorage.put(statistics.getId(), statistics);
        return statistics;
    }

    public Statistics getById(Long id) {
        return statisticsStorage.get(id);
    }

    public List<Statistics> getAll() {
        return new ArrayList<>(statisticsStorage.values());
    }

    public Statistics update(Long id, Statistics statistics) {
        Statistics existing = statisticsStorage.get(id);
        if (existing != null) {
            statistics.setId(id);
            statistics.setCreatedAt(existing.getCreatedAt());
            statistics.setUpdatedAt(System.currentTimeMillis());
            statisticsStorage.put(id, statistics);
            return statistics;
        }
        return null;
    }

    public boolean delete(Long id) {
        return statisticsStorage.remove(id) != null;
    }
}
