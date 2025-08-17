package org.dynamik.service;

import org.dynamik.exception.ResourceNotFoundException;
import org.dynamik.model.Message;
import org.dynamik.model.Publisher;
import org.dynamik.model.Subscriber;
import org.dynamik.model.Topic;

public class PubSubSystem implements IPubSubSystem {

    private static  PubSubSystem pubSubSystem;
    private TopicService topicService = new TopicService();
    private MessageService messageService = new MessageService();
    private SubscriberService subscriberService = new SubscriberService();
    private PublisherService publisherService = new PublisherService();

    public static PubSubSystem getInstance() {
        if (pubSubSystem == null) {
            synchronized (PubSubSystem.class) {
                if (pubSubSystem == null) {
                    pubSubSystem = new PubSubSystem();
                }
            }
        }

        return pubSubSystem;
    }


    @Override
    public void publish(String topicId, String messageId, String publisherId) {
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

         message.setPublisherId(publisherId);
         message.setTopicId(topicId);
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
    public Topic createTopic(String topicName) {
        return topicService.save(new Topic(topicName));
    }
}
