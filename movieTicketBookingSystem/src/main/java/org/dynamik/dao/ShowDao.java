package org.dynamik.dao;

import org.dynamik.model.Show;

import java.util.*;

public class ShowDao {
    private static final Map<String, Show> shows = new HashMap<>();

    public void save(Show show) {
        if (show.getId() == null) {
            show.setId(UUID.randomUUID().toString());
        }
        shows.put(show.getId(), show);
    }

    public Show findById(String id) {
        return shows.get(id);
    }

    public List<Show> findAll() {
        return new ArrayList<>(shows.values());
    }

    public void delete(String id) {
        shows.remove(id);
    }

    public List<Show> findByMovieId(String movieId) {
        return shows.values().stream()
                .filter(show -> show.getMovieId().equals(movieId))
                .toList();
    }

    public List<Show> findByTheatreId(String theatreId) {
        return shows.values().stream()
                .filter(show -> show.getTheatreId().equals(theatreId))
                .toList();
    }
}
