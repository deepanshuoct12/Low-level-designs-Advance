package org.dynamik.service;

import org.dynamik.model.Message;
import org.dynamik.model.Subscriber;
import org.dynamik.model.Topic;

public interface IPubSubSystem {
    void publish(String topicId, String messageId, String publisherId);
    void subscribe(String topicId, String subscriberId);
    void unsubscribe(String topicId, String subscriberId);
    Topic createTopic(String topicName);
}
