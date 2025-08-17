package org.dynamik.dao;

import org.dynamik.model.Message;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageDao implements BaseDao <Message, String> {
    private static Map<String, Message> messages = new ConcurrentHashMap<>();

    public List<Message> findByTopicId(String topicId) {
         return messages.values().stream().filter(m -> m.getTopicId().equals(topicId)).toList();
    }

    public List<Message> findByPublisherId(String publisherId) {
         return messages.values().stream().filter(m -> m.getPublisherId().equals(publisherId)).toList();
    }


    @Override
    public Message save(Message entity) {
        return messages.put(entity.getId(), entity);
    }

    @Override
    public Message findById(String s) {
        return messages.get(s);
    }

    @Override
    public void delete(Message entity) {
        messages.remove(entity.getId());
    }

    @Override
    public void deleteById(String s) {
        messages.remove(s);
    }

    @Override
    public boolean existsById(String s) {
        return messages.containsKey(s);
    }
}
