package org.dynamik.demo;

import org.dynamik.model.Message;
import org.dynamik.model.Publisher;
import org.dynamik.model.Subscriber;
import org.dynamik.model.Topic;
import org.dynamik.service.*;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PubSubDriver {
    private static final PubSubSystem pubSubSystem = PubSubSystem.getInstance();
    private final PublisherService publisherService = new PublisherService();
    private final SubscriberService subscriberService = new SubscriberService();
    private final TopicService topicService = new TopicService();
    private final Random random = new Random();
    private final MessageService messageService  = new MessageService();

    public void runDemo() {
        // Initialize the demo
        System.out.println("🚀 Starting Pub-Sub System Demo...\n");

        // Create topics
        Topic techNews = createTopic("Tech News");
        Topic sports = createTopic("Sports");
        Topic finance = createTopic("Finance");

        // Create publishers
        Publisher techPublisher = createPublisher("Tech Publisher");
        Publisher sportsPublisher = createPublisher("Sports Publisher");
        Publisher financePublisher = createPublisher("Finance Publisher");

        // Create subscribers
        Subscriber subscriber1 = createSubscriber("Alice");
        Subscriber subscriber2 = createSubscriber("Bob");
        Subscriber subscriber3 = createSubscriber("Charlie");

        // Subscribe users to topics
        subscribeToTopic(techNews, subscriber1);
        subscribeToTopic(techNews, subscriber2);
        subscribeToTopic(sports, subscriber2);
        subscribeToTopic(sports, subscriber3);
        subscribeToTopic(finance, subscriber1);
        subscribeToTopic(finance, subscriber3);

        // Simulate concurrent publishing and subscribing
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Publisher threads
        simulatePublisher(techPublisher, techNews, "Tech Update #%d: New AI breakthrough!");
        simulatePublisher(sportsPublisher, sports, "Sports Update #%d: Exciting match results!");
        simulatePublisher(financePublisher, finance, "Market Update #%d: Stock market analysis");

        // Add a new subscriber after some time
      //  executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                Subscriber lateSubscriber = createSubscriber("Late Joiner");
                subscribeToTopic(techNews, lateSubscriber);
                subscribeToTopic(finance, lateSubscriber);
                System.out.println("\n🆕 Late Joiner subscribed to Tech News and Finance!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
   //     });

        // Unsubscribe a user after some time
      //  executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(8);
                System.out.println("\n👋 " + subscriber2.getName() + " is unsubscribing from Tech News...");
                pubSubSystem.unsubscribe(techNews.getId(), subscriber2.getId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    //    });

        // Shutdown after some time
        executor.shutdown();
        try {
            if (!executor.awaitTermination(15, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
            System.out.println("\n✅ Demo completed!");
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private  Topic createTopic(String name) {
        System.out.println("📢 Creating topic: " + name);
        return pubSubSystem.createTopic(name);
    }

    private  Publisher createPublisher(String name) {
        Publisher publisher = new Publisher();
        publisher.setId(UUID.randomUUID().toString());
        publisher.setName(name);
        publisherService.save(publisher);
        System.out.println("📝 Created publisher: " + name);
        return publisher;
    }

    private  Subscriber createSubscriber(String name) {
        Subscriber subscriber = new Subscriber();
        subscriber.setId(UUID.randomUUID().toString());
        subscriber.setName(name);
        subscriberService.save(subscriber);
        System.out.println("👤 Created subscriber: " + name);
        return subscriber;
    }

    private  void subscribeToTopic(Topic topic, Subscriber subscriber) {
        System.out.println("🔔 " + subscriber.getName() + " subscribed to " + topic.getTopicName());
        pubSubSystem.subscribe(topic.getId(), subscriber.getId());
    }

    private  void simulatePublisher(Publisher publisher, Topic topic, String messageFormat) {
        try {
            for (int i = 1; i <= 5; i++) {
                String messageContent = String.format(messageFormat, i);
                Message message = new Message();
                message.setId(UUID.randomUUID().toString());
                message.setContent(messageContent);
                message.setPublisherId(publisher.getId());
                message.setTopicId(topic.getId());
                messageService.save(message);
                
                System.out.println("\n📤 " + publisher.getName() + " publishing to " + topic.getTopicName() + ": " + messageContent);
                pubSubSystem.publish(topic.getId(), message.getId(), publisher.getId());
                
                // Random delay between 1-3 seconds
                TimeUnit.SECONDS.sleep(1 + random.nextInt(3));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
