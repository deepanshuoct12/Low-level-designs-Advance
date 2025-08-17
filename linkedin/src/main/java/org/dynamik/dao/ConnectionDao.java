package org.dynamik.dao;

import org.dynamik.enums.ConnectionRequestState;
import org.dynamik.model.Connection;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of Connection data access object using an in-memory HashMap.
 * This class provides CRUD operations for Connection entities.
 */
public class ConnectionDao implements IBaseDao<Connection, String> {
    private static final Map<String, Connection> connections = new HashMap<>();
    private static final Map<String, List<Connection>> connectionsByUserId = new HashMap<>();

    @Override
    public Connection save(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        if (connection.getId() == null || connection.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Connection ID cannot be null or empty");
        }
        connections.put(connection.getId(), connection);

        if (connection.getState() == ConnectionRequestState.ACCEPTED) {
            connectionsByUserId.computeIfAbsent(connection.getUserId(), k -> new ArrayList<>()).add(connection);
        }

        return connection;
    }

    @Override
    public List<Connection> findAll() {
        return new ArrayList<>(connections.values());
    }

    @Override
    public void delete(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Connection ID cannot be null or empty");
        }
        connections.remove(id);
    }

    @Override
    public Connection findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Connection ID cannot be null or empty");
        }
        return connections.get(id);
    }

    /**
     * Find all connections for a specific user
     * @param userId the ID of the user
     * @return list of connections for the specified user
     */
    public List<Connection> findByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        return connectionsByUserId.getOrDefault(userId, Collections.emptyList());
    }

    /**
     * Find all connections with a specific state for a user
     * @param userId the ID of the user
     * @param state the connection state to filter by
     * @return list of matching connections
     */
    public List<Connection> findByUserIdAndState(String userId, ConnectionRequestState state) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
        return connections.values().stream()
                .filter(conn -> userId.equals(conn.getUserId()) && state.equals(conn.getState()))
                .collect(Collectors.toList());
    }

    /**
     * Find a specific connection between two users
     * @param userId1 the ID of the first user
     * @param userId2 the ID of the second user
     * @return the connection if found, null otherwise
     */
    public Connection findConnectionBetween(String userId1, String userId2) {
        if (userId1 == null || userId1.trim().isEmpty() || userId2 == null || userId2.trim().isEmpty()) {
            throw new IllegalArgumentException("User IDs cannot be null or empty");
        }
        return connections.values().stream()
                .filter(conn -> 
                    (userId1.equals(conn.getUserId()) && userId2.equals(conn.getConnectionId())) ||
                    (userId2.equals(conn.getUserId()) && userId1.equals(conn.getConnectionId()))
                )
                .findFirst()
                .orElse(null);
    }
}
