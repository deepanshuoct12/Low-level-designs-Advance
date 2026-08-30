package org.project.strategy;

import org.project.model.Player;

import java.util.List;

public interface PlayerSearchStrategy {
    List<Player> search(List<Player> players, String criteria);
}
