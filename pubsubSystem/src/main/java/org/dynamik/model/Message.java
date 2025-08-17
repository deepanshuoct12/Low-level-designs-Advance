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
}
