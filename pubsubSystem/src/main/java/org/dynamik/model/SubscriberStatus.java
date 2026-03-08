package org.dynamik.model;

import lombok.Data;

@Data
public class SubscriberStatus {
    private String topicName;
    private Long lastConsumedOffset;
    private Long topicCurrentOffset;
    private Long lag;
}
