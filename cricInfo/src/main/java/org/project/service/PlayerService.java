package org.project.service;

import org.project.model.Player;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

public class PlayerService {
    private static final ConcurrentHashMap<Long, Player> playerStorage = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public Player create(Player player) {
        if (player.getId() == null) {
            player.setId(idCounter++);
        }
        player.setCreatedAt(System.currentTimeMillis());
        player.setUpdatedAt(System.currentTimeMillis());
        playerStorage.put(player.getId(), player);
        return player;
    }

    public Player getById(Long id) {
        return playerStorage.get(id);
    }

    public List<Player> getAll() {
        return new ArrayList<>(playerStorage.values());
    }

    public Player update(Long id, Player player) {
        Player existing = playerStorage.get(id);
        if (existing != null) {
            player.setId(id);
            player.setCreatedAt(existing.getCreatedAt());
            player.setUpdatedAt(System.currentTimeMillis());
            playerStorage.put(id, player);
            return player;
        }
        return null;
    }

    public boolean delete(Long id) {
        return playerStorage.remove(id) != null;
    }
}
