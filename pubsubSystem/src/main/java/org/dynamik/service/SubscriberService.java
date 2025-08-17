package org.dynamik.service;

import org.dynamik.dao.SubscriberDao;
import org.dynamik.model.Subscriber;

public class SubscriberService {
    private final SubscriberDao subscriberDao;

    public SubscriberService() {
        this.subscriberDao = new SubscriberDao();
    }

    // Create
    public Subscriber save(Subscriber subscriber) {
        return subscriberDao.save(subscriber);
    }

    // Read
    public Subscriber findById(String id) {
        return subscriberDao.findById(id);
    }

    public Subscriber findByName(String name) {
        return subscriberDao.findByName(name);
    }

    // Update
    public Subscriber update(Subscriber subscriber) {
        return subscriberDao.save(subscriber);
    }

    // Delete
    public void delete(Subscriber subscriber) {
        subscriberDao.delete(subscriber);
    }

    public void deleteById(String id) {
        subscriberDao.deleteById(id);
    }

    // Exists
    public boolean existsById(String id) {
        return subscriberDao.existsById(id);
    }
}
