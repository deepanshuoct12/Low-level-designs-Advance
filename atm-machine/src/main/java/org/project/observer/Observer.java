package org.project.observer;

public interface Observer {
    void update(String transactionType, Long amount, String status);
}
