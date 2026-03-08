package org.dynamik.model;

import lombok.Data;
import org.dynamik.observer.IObserver;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Subscriber implements IObserver {
    private String id;
    private Long updatedAt;
    private Long createdAt;
    private String name;
    
    // Offset management: topicId -> set of consumed message offsets
    private ConcurrentHashMap<String, Set<Long>> consumedOffsets = new ConcurrentHashMap<>();

    @Override
    public void update(Message message) {
     System.out.println("Subscriber " + name + " has received a message: " + message.getContent());
     // Track consumed message offset
     consumeMessage(message.getTopicId(), message.getOffset());
    }
    
    public void consumeMessage(String topicId, Long messageOffset) {
        consumedOffsets.computeIfAbsent(topicId, k -> ConcurrentHashMap.newKeySet()).add(messageOffset);
    }
    
    public Set<Long> getConsumedOffsets(String topicId) {
        return consumedOffsets.computeIfAbsent(topicId, k -> ConcurrentHashMap.newKeySet());
    }
    
    public boolean hasConsumed(String topicId, Long messageOffset) {
        return getConsumedOffsets(topicId).contains(messageOffset);
    }
    
    public void resetOffsets(String topicId) {
        consumedOffsets.computeIfAbsent(topicId, k -> ConcurrentHashMap.newKeySet()).clear();
        System.out.println("🔄 Reset offsets for subscriber " + name + " on topic " + topicId);
    }
}
