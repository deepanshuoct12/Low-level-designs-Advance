package org.project.service;

import org.project.model.MatchSchedule;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

public class MatchScheduleService {
    private static final ConcurrentHashMap<Long, MatchSchedule> matchScheduleStorage = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public MatchSchedule create(MatchSchedule matchSchedule) {
        if (matchSchedule.getId() == null) {
            matchSchedule.setId(idCounter++);
        }
        matchSchedule.setCreatedAt(System.currentTimeMillis());
        matchSchedule.setUpdatedAt(System.currentTimeMillis());
        matchScheduleStorage.put(matchSchedule.getId(), matchSchedule);
        return matchSchedule;
    }

    public MatchSchedule getById(Long id) {
        return matchScheduleStorage.get(id);
    }

    public List<MatchSchedule> getAll() {
        return new ArrayList<>(matchScheduleStorage.values());
    }

    public MatchSchedule update(Long id, MatchSchedule matchSchedule) {
        MatchSchedule existing = matchScheduleStorage.get(id);
        if (existing != null) {
            matchSchedule.setId(id);
            matchSchedule.setCreatedAt(existing.getCreatedAt());
            matchSchedule.setUpdatedAt(System.currentTimeMillis());
            matchScheduleStorage.put(id, matchSchedule);
            return matchSchedule;
        }
        return null;
    }

    public boolean delete(Long id) {
        return matchScheduleStorage.remove(id) != null;
    }
}
