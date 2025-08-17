package org.dynamik.service;

import org.dynamik.dao.ConnectionDao;
import org.dynamik.enums.ConnectionRequestState;
import org.dynamik.model.Connection;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ConnectionService {
    private final ConnectionDao connectionDao;

    public ConnectionService() {
        this.connectionDao = new ConnectionDao();
    }

    public Connection sendConnectionRequest(Connection connection) {
        // Check if connection already exists
        Connection existing = connectionDao.findConnectionBetween(
            connection.getUserId(),
            connection.getConnectionId()
        );
        
        if (existing != null) {
            throw new IllegalStateException("Connection request already exists");
        }

        connection.setId(UUID.randomUUID().toString());
        return connectionDao.save(connection);
    }

    public void acceptConnection(String connectionId) {
        Connection connection = connectionDao.findById(connectionId);
        if (connection != null) {
            connection.setState(ConnectionRequestState.ACCEPTED);
            connectionDao.save(connection);
        }
    }

    public void rejectConnection(String connectionId) {
        connectionDao.delete(connectionId);
    }

    public List<Connection> getConnectionsByUser(String userId) {
        return connectionDao.findByUserId(userId);
        //return connectionDao.findByUserIdAndState(userId, ConnectionRequestState.ACCEPTED);
    }

    public List<Connection> getConnectionRequests(String userId) {
        return connectionDao.findByUserIdAndState(userId, ConnectionRequestState.PENDING);
    }

    public void removeConnection(String connectionId) {
        connectionDao.delete(connectionId);
    }
}
