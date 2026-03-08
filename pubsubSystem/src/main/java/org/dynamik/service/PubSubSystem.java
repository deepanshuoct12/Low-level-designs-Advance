package org.dynamik.service;

import org.dynamik.exception.ResourceNotFoundException;
import org.dynamik.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Collections;

public class PubSubSystem implements IPubSubSystem {

    private static  PubSubSystem pubSubSystem;
    private TopicService topicService = new TopicService();
    private MessageService messageService = new MessageService();
    private SubscriberService subscriberService = new SubscriberService();
    private PublisherService publisherService = new PublisherService();
    private ScheduledDeletionService deletionService = new ScheduledDeletionService(messageService);

    public static PubSubSystem getInstance() {
        if (pubSubSystem == null) {
            synchronized (PubSubSystem.class) {
                if (pubSubSystem == null) {
                    pubSubSystem = new PubSubSystem();
                    // Auto-start cleanup scheduler
                    pubSubSystem.deletionService.start();
                }
            }
        }

        return pubSubSystem;
    }
    
    public void startCleanupScheduler() {
        deletionService.start();
    }
    
    public void stopCleanupScheduler() {
        deletionService.stop();
    }


    @Override
    public  void publish(String topicId, String messageId, String publisherId) {
         Topic topic = topicService.findById(topicId);
         if (topic == null) {
             throw new ResourceNotFoundException("Topic not found with id: " + topicId);
         }

         Message message = messageService.findById(messageId);
         if (message == null) {
             throw new ResourceNotFoundException("Message not found with id: " + messageId);
         }

         Publisher publisher = publisherService.findById(publisherId);
         if (publisher == null) {
             throw new ResourceNotFoundException("Publisher not found with id: " + publisherId);
         }

         // Assign offset to message and save
         message.setPublisherId(publisherId);
         message.setTopicId(topicId);
         message.setOffset(topic.getNextOffset().getAndIncrement());
         message.setUpdatedAt(System.currentTimeMillis());
         
         // Set deletion time based on topic retention
         if (topic.getRetentionTime() != null && topic.getRetentionTime() > 0) {
             message.setDeletionTime(System.currentTimeMillis() + topic.getRetentionTime());
         }
         
         messageService.save(message);
         topic.notifyObservers(message);
    }

    @Override
    public void subscribe(String topicId, String subscriberId) {
        Topic topic = topicService.findById(topicId);
        if (topic == null) {
            throw new ResourceNotFoundException("Topic not found with id: " + topicId);
        }

        Subscriber subscriber = subscriberService.findById(subscriberId);
        if (subscriber == null) {
            throw new ResourceNotFoundException("Subscriber not found with id: " + subscriberId);
        }

        topic.addObserver(subscriber);
    }

    @Override
    public void unsubscribe(String topicId, String subscriberId) {
       Topic topic = topicService.findById(topicId);
       if (topic == null) {
           throw new ResourceNotFoundException("Topic not found with id: " + topicId);
       }

       Subscriber subscriber = subscriberService.findById(subscriberId);
       if (subscriber == null) {
           throw new ResourceNotFoundException("Subscriber not found with id: " + subscriberId);
       }

       topic.removeObserver(subscriber);
    }

    @Override
    public Topic createTopic(String topicName, Long retentionTimeMillis) {
        return topicService.save(new Topic(topicName, retentionTimeMillis));
    }

    @Override
    public void deleteTopic(String topicId) {
        if (!topicService.existsById(topicId)) {
            throw new ResourceNotFoundException("Topic not found with id : " + topicId);
        }

        topicService.deleteById(topicId);
    }

    @Override
    public List<SubscriberStatus> getSubscriberStats(String subcriberId) {
        Subscriber subscriber = subscriberService.findById(subcriberId);
        List<String> topicIds =  subscriber.getConsumedOffsets().keySet().stream().toList();
        List<Topic> topics = topicService.findAllByIds(topicIds);

        List<SubscriberStatus> subscriberStatuses = new ArrayList<>();
        for (Topic topic : topics) {
            SubscriberStatus subscriberStatus = new SubscriberStatus();
            subscriberStatus.setTopicName(topic.getTopicName());
            
            // Get last consumed offset (max value from set)
            Set<Long> consumedOffsets = subscriber.getConsumedOffsets(topic.getId());
            Long lastConsumedOffset = Collections.max(consumedOffsets);
            subscriberStatus.setLastConsumedOffset(lastConsumedOffset);

            // Get current topic offset
            subscriberStatus.setTopicCurrentOffset(topic.getNextOffset().get() - 1);
            subscriberStatus.setLag(subscriberStatus.getTopicCurrentOffset() - subscriberStatus.getLastConsumedOffset());
            subscriberStatuses.add(subscriberStatus);
        }

        return subscriberStatuses;
    }

    @Override
    public void resetOffset(String topicId, String subscriberId) {
        Topic topic = topicService.findById(topicId);
        if (topic == null) {
            throw new ResourceNotFoundException("Topic not found with id: " + topicId);
        }
        
        Subscriber subscriber = subscriberService.findById(subscriberId);
        if (subscriber == null) {
            throw new ResourceNotFoundException("Subscriber not found with id: " + subscriberId);
        }
        
        subscriber.resetOffsets(topicId);
        subscriberService.save(subscriber);
    }
    
    @Override
    public List<Message> replayFromOffset(String topicId, String subscriberId, Long fromOffset) {
        Topic topic = topicService.findById(topicId);
        if (topic == null) {
            throw new ResourceNotFoundException("Topic not found with id: " + topicId);
        }
        
        Subscriber subscriber = subscriberService.findById(subscriberId);
        if (subscriber == null) {
            throw new ResourceNotFoundException("Subscriber not found with id: " + subscriberId);
        }
        
        // Get all messages for the topic from the specified offset
        List<Message> allMessages = messageService.findByTopicId(topicId);
        return allMessages.stream()
                .filter(msg -> msg.getOffset() >= fromOffset)
                .sorted((m1, m2) -> Long.compare(m1.getOffset(), m2.getOffset()))
                .toList();
    }
}
