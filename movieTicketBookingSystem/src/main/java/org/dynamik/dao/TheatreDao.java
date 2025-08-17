package org.dynamik.dao;

import org.dynamik.model.Theatre;

import java.util.*;

public class TheatreDao {
    private static final Map<String, Theatre> theatres = new HashMap<>();

    public void save(Theatre theatre) {
        if (theatre.getId() == null) {
            theatre.setId(UUID.randomUUID().toString());
        }
        theatres.put(theatre.getId(), theatre);
    }

    public Theatre findById(String id) {
        return theatres.get(id);
    }

    public List<Theatre> findAll() {
        return new ArrayList<>(theatres.values());
    }

    public void delete(String id) {
        theatres.remove(id);
    }

    public List<Theatre> findByName(String name) {
        return theatres.values().stream()
                .filter(theatre -> theatre.getName().equalsIgnoreCase(name))
                .toList();
    }

    public List<Theatre> findByLocation(String location) {
        return theatres.values().stream()
                .filter(theatre -> theatre.getLocation().equalsIgnoreCase(location))
                .toList();
    }
}
