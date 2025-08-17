package org.dynamik.dao;

import org.dynamik.model.Movie;

import java.util.*;

public class MovieDao {
    private static final Map<String, Movie> movies = new HashMap<>();

    public void save(Movie movie) {
        if (movie.getId() == null) {
            movie.setId(UUID.randomUUID().toString());
        }
        movies.put(movie.getId(), movie);
    }

    public Movie findById(String id) {
        return movies.get(id);
    }

    public List<Movie> findAll() {
        return new ArrayList<>(movies.values());
    }

    public void delete(String id) {
        movies.remove(id);
    }

    public List<Movie> findByTitle(String title) {
        return movies.values().stream()
                .filter(movie -> movie.getTitle().equalsIgnoreCase(title))
                .toList();
    }

    public List<Movie> findByGenre(String genre) {
        return movies.values().stream()
                .filter(movie -> movie.getGenre().equalsIgnoreCase(genre))
                .toList();
    }
}
