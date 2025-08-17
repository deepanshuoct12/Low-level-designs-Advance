package org.dynamik.dao;

import org.dynamik.model.Publisher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PublisherDao implements BaseDao<Publisher, String> {
    private static final Map<String, Publisher> publishers = new ConcurrentHashMap<>();
    private static final Map<String, Publisher> publishersByName = new ConcurrentHashMap<>();

    public Publisher findByName(String name) {
        return publishersByName.get(name);
    }

    @Override
    public Publisher save(Publisher publisher) {
        publishers.put(publisher.getId(), publisher);
        publishersByName.put(publisher.getName(), publisher);
        return publisher;
    }

    @Override
    public Publisher findById(String id) {
        return publishers.get(id);
    }

    @Override
    public void delete(Publisher publisher) {
        publishers.remove(publisher.getId());
        publishersByName.remove(publisher.getName());
    }

    @Override
    public void deleteById(String id) {
        Publisher publisher = publishers.get(id);
        if (publisher != null) {
            publishersByName.remove(publisher.getName());
        }
        publishers.remove(id);
    }

    @Override
    public boolean existsById(String id) {
        return publishers.containsKey(id);
    }
}
