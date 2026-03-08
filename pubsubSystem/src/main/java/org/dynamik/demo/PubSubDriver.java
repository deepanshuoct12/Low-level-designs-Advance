package org.dynamik.demo;

import org.dynamik.model.*;
import org.dynamik.service.*;

import java.util.List;
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

        // Create topics with short retention times for demo
        Topic techNews = createTopic("Tech News", 5000L);   // 5 seconds retention
//        Topic sports = createTopic("Sports", 8000L);        // 8 seconds retention
//        Topic finance = createTopic("Finance", 10000L);      // 10 seconds retention

        // Create publishers
        Publisher techPublisher = createPublisher("Tech Publisher");
//        Publisher sportsPublisher = createPublisher("Sports Publisher");
//        Publisher financePublisher = createPublisher("Finance Publisher");

        // Create subscribers
        Subscriber subscriber1 = createSubscriber("Alice");
        Subscriber subscriber2 = createSubscriber("Bob");
        Subscriber subscriber3 = createSubscriber("Charlie");

        // Subscribe users to topics
        subscribeToTopic(techNews, subscriber1);
        subscribeToTopic(techNews, subscriber2);
//        subscribeToTopic(sports, subscriber2);
//        subscribeToTopic(sports, subscriber3);
//        subscribeToTopic(finance, subscriber1);
//        subscribeToTopic(finance, subscriber3);

        //simulate publisher
       simulatePublisher(techPublisher, techNews, "Tech Update #%d: New AI breakthrough!");
//
//
//        // simulate parallel publisher
//        simulateParallelPublisher(financePublisher, finance, "Market Update #%d: Stock market analysis");

        // Demo message deletion with retention
//        demoMessageDeletion(techPublisher, techNews, "Tech Update #%d: New AI breakthrough!");

        // Test subscriber status
        testSubscriberStatus(subscriber1.getId());

        // Shutdown after demo
        try {
            TimeUnit.SECONDS.sleep(15); // Wait for deletion demo
            System.out.println("\n✅ Demo completed!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        // Add a new subscriber after some time
//      //  executor.submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(5);
//                Subscriber lateSubscriber = createSubscriber("Late Joiner");
//                subscribeToTopic(techNews, lateSubscriber);
//                subscribeToTopic(finance, lateSubscriber);
//                System.out.println("\n🆕 Late Joiner subscribed to Tech News and Finance!");
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//   //     });

        // Unsubscribe a user after some time
//      //  executor.submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(8);
//                System.out.println("\n👋 " + subscriber2.getName() + " is unsubscribing from Tech News...");
//                pubSubSystem.unsubscribe(techNews.getId(), subscriber2.getId());
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//    //    });

        // Shutdown after some time
//        executor.shutdown();
//        try {
//            if (!executor.awaitTermination(15, TimeUnit.SECONDS)) {
//                executor.shutdownNow();
//            }
//            System.out.println("\n✅ Demo completed!");
//        } catch (InterruptedException e) {
//            executor.shutdownNow();
//            Thread.currentThread().interrupt();
//        }
    }

    private void simulateParallelPublisher(Publisher publisher, Topic topic, String messageFormat) {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            executor.submit(() -> {
                String messageContent = String.format(messageFormat, finalI);
                Message message = new Message();
                message.setId(UUID.randomUUID().toString());
                message.setContent(messageContent);
                message.setPublisherId(publisher.getId());
                message.setTopicId(topic.getId());
                messageService.save(message);

                System.out.println("\n📤 " + publisher.getName() + " publishing to " + topic.getTopicName() + ": " + messageContent);
                pubSubSystem.publish(topic.getId(), message.getId(), publisher.getId());
            });
        }
    }
    
    private void demoMessageDeletion(Publisher publisher, Topic topic, String messageFormat) {
        System.out.println("\n🧪 Starting deletion demo for topic: " + topic.getTopicName() + " (Retention: " + topic.getRetentionTime() + "ms)");
        
        try {
            // Publish 3 messages with timestamps
            for (int i = 1; i <= 3; i++) {
                String messageContent = String.format(messageFormat, i);
                Message message = new Message();
                message.setId(UUID.randomUUID().toString());
                message.setContent(messageContent);
                message.setPublisherId(publisher.getId());
                message.setTopicId(topic.getId());
                messageService.save(message);
                
                System.out.println("📤 [" + System.currentTimeMillis() + "] " + publisher.getName() + " publishing: " + messageContent);
                pubSubSystem.publish(topic.getId(), message.getId(), publisher.getId());
                
                // Check message count after publishing
                List<Message> currentMessages = messageService.findByTopicId(topic.getId());
                System.out.println("📊 Messages in topic after publish: " + currentMessages.size());
                
                TimeUnit.SECONDS.sleep(2); // Wait 2 seconds between messages
            }
            
            // Wait and check if messages are deleted
            System.out.println("\n⏳ Waiting for messages to expire based on retention time...");
            TimeUnit.SECONDS.sleep(8); // Wait longer than retention time
            
            // Check final message count
            List<Message> finalMessages = messageService.findByTopicId(topic.getId());
            System.out.println("📊 Final messages in topic: " + finalMessages.size());
            
            if (finalMessages.isEmpty()) {
                System.out.println("✅ All messages deleted successfully by retention policy!");
            } else {
                System.out.println("⚠️  " + finalMessages.size() + " messages still present (may not have expired yet)");
                finalMessages.forEach(msg -> System.out.println("  - " + msg.getContent() + " (deletes at: " + (msg.getDeletionTime() != null ? new java.util.Date(msg.getDeletionTime()) : "N/A") + ")"));
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void testSubscriberStatus(String subscriberId) {
        System.out.println("\n📊 Testing Subscriber Status for: " + subscriberId);
        
        try {
            List<SubscriberStatus> statuses = pubSubSystem.getSubscriberStats(subscriberId);
            
            if (statuses.isEmpty()) {
                System.out.println("ℹ️  No topic subscriptions found for this subscriber");
                return;
            }
            
            System.out.println("📈 Subscriber Status Report:");
            System.out.println("================================");
            
            for (SubscriberStatus status : statuses) {
                System.out.println("📢 Topic: " + status.getTopicName());
                System.out.println("   Last Consumed Offset: " + status.getLastConsumedOffset());
                System.out.println("   Current Topic Offset: " + status.getTopicCurrentOffset());
                System.out.println("   Lag: " + status.getLag() + " messages");
                System.out.println("   Status: " + getLagDescription(status.getLag()));
                System.out.println();
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error getting subscriber status: " + e.getMessage());
        }
    }
    
    private String getLagDescription(Long lag) {
        if (lag == 0) return "CAUGHT_UP ✅";
        if (lag <= 5) return "ACTIVE 🟢";
        if (lag <= 20) return "LAGGING 🟡";
        return "HEAVILY_LAGGING 🔴";
    }

    private Topic createTopic(String name, Long retentionTimeMillis) {
        System.out.println("📢 Creating topic: " + name + " (Retention: " + (retentionTimeMillis/1000) + " seconds)");
        return pubSubSystem.createTopic(name, retentionTimeMillis);
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
