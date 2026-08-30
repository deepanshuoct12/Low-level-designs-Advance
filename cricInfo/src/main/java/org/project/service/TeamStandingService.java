package org.project.service;

import org.project.model.TeamStanding;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

public class TeamStandingService {
    private static final ConcurrentHashMap<Long, TeamStanding> teamStandingStorage = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public TeamStanding create(TeamStanding teamStanding) {
        if (teamStanding.getId() == null) {
            teamStanding.setId(idCounter++);
        }
        teamStanding.setCreatedAt(System.currentTimeMillis());
        teamStanding.setUpdatedAt(System.currentTimeMillis());
        teamStandingStorage.put(teamStanding.getId(), teamStanding);
        return teamStanding;
    }

    public TeamStanding getById(Long id) {
        return teamStandingStorage.get(id);
    }

    public List<TeamStanding> getAll() {
        return new ArrayList<>(teamStandingStorage.values());
    }

    public TeamStanding update(Long id, TeamStanding teamStanding) {
        TeamStanding existing = teamStandingStorage.get(id);
        if (existing != null) {
            teamStanding.setId(id);
            teamStanding.setCreatedAt(existing.getCreatedAt());
            teamStanding.setUpdatedAt(System.currentTimeMillis());
            teamStandingStorage.put(id, teamStanding);
            return teamStanding;
        }
        return null;
    }

    public boolean delete(Long id) {
        return teamStandingStorage.remove(id) != null;
    }
}
