package org.dynamik.model;

import lombok.Data;

@Data
public class Message {
    private String id;
    private Long updatedAt;
    private Long createdAt;
    private String content;
    private String publisherId;
    private String topicId;
    private Long ttl;  // RETENTION TIME
    private Long offset; // Message offset within topic (sequence number)
    private Long deletionTime; // When this message should be deleted (scheduled)
}
