package org.dynamik.service;

import org.dynamik.dao.PublisherDao;
import org.dynamik.model.Publisher;

public class PublisherService {
    private final PublisherDao publisherDao;

    public PublisherService() {
        this.publisherDao = new PublisherDao();
    }

    // Create
    public Publisher save(Publisher publisher) {
        return publisherDao.save(publisher);
    }

    // Read
    public Publisher findById(String id) {
        return publisherDao.findById(id);
    }

    public Publisher findByName(String name) {
        return publisherDao.findByName(name);
    }

    // Update
    public Publisher update(Publisher publisher) {
        return publisherDao.save(publisher);
    }

    // Delete
    public void delete(Publisher publisher) {
        publisherDao.delete(publisher);
    }

    public void deleteById(String id) {
        publisherDao.deleteById(id);
    }

    // Exists
    public boolean existsById(String id) {
        return publisherDao.existsById(id);
    }
}
