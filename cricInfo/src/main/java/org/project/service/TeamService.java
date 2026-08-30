package org.project.service;

import org.project.model.Team;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

public class TeamService {
    private static final ConcurrentHashMap<Long, Team> teamStorage = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public Team create(Team team) {
        if (team.getId() == null) {
            team.setId(idCounter++);
        }
        team.setCreatedAt(System.currentTimeMillis());
        team.setUpdatedAt(System.currentTimeMillis());
        teamStorage.put(team.getId(), team);
        return team;
    }

    public Team getById(Long id) {
        return teamStorage.get(id);
    }

    public List<Team> getAll() {
        return new ArrayList<>(teamStorage.values());
    }

    public Team update(Long id, Team team) {
        Team existing = teamStorage.get(id);
        if (existing != null) {
            team.setId(id);
            team.setCreatedAt(existing.getCreatedAt());
            team.setUpdatedAt(System.currentTimeMillis());
            teamStorage.put(id, team);
            return team;
        }
        return null;
    }

    public boolean delete(Long id) {
        return teamStorage.remove(id) != null;
    }
}
