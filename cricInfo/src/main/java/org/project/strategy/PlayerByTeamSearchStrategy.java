package org.project.strategy;

import org.project.model.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerByTeamSearchStrategy implements PlayerSearchStrategy {
    @Override
    public List<Player> search(List<Player> players, String criteria) {
        Long teamId = Long.parseLong(criteria);
        return players.stream()
                .filter(player -> player.getTeamId().equals(teamId))
                .collect(Collectors.toList());
    }
}
