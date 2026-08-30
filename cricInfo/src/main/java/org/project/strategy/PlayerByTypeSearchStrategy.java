package org.project.strategy;

import org.project.enums.PlayerType;
import org.project.model.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerByTypeSearchStrategy implements PlayerSearchStrategy {
    @Override
    public List<Player> search(List<Player> players, String criteria) {
        PlayerType type = PlayerType.valueOf(criteria.toUpperCase());
        return players.stream()
                .filter(player -> player.getPlayerType() == type)
                .collect(Collectors.toList());
    }
}
