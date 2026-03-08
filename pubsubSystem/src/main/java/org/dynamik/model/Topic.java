package org.dynamik.model;

import lombok.Data;
import org.dynamik.observer.Subject;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class Topic extends Subject {
    private String id;
    private Long updatedAt;
    private Long createdAt;
    private String topicName;
    private Long retentionTime;
    private AtomicLong nextOffset = new AtomicLong(0); // Next message offset for this topic

    public Topic(String topicName, Long retentionTimeMillis) {
        this.topicName = topicName;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.retentionTime = retentionTimeMillis;
        this.id = UUID.randomUUID().toString();
    }
}
