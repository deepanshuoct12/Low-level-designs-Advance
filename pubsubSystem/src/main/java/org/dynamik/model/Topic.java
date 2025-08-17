package org.dynamik.model;

import lombok.Data;
import org.dynamik.observer.Subject;

import java.util.UUID;

@Data
public class Topic extends Subject {
    private String id;
    private Long updatedAt;
    private Long createdAt;
    private String topicName;

    public Topic(String topicName) {
        this.topicName = topicName;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.id = UUID.randomUUID().toString();
    }
}
