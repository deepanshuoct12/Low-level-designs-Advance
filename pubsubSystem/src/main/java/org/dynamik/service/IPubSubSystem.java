package org.dynamik.service;

import org.dynamik.model.Message;
import org.dynamik.model.SubscriberStatus;
import org.dynamik.model.Topic;

import java.util.List;

public interface IPubSubSystem {
    void publish(String topicId, String messageId, String publisherId);
    void subscribe(String topicId, String subscriberId);
    void unsubscribe(String topicId, String subscriberId);
    Topic createTopic(String topicName, Long retentionTimeMillis);
    void deleteTopic(String topicId);
    List<SubscriberStatus> getSubscriberStats(String subcriberId);

    // Offset management methods
    void resetOffset(String topicId, String subscriberId);
    List<Message> replayFromOffset(String topicId, String subscriberId, Long fromOffset);
}
