package org.dynamik.service;

import org.dynamik.dao.MovieDao;
import org.dynamik.model.Movie;

import java.util.List;

public class MovieService implements BaseService<Movie, String> {
    private MovieDao movieDao;
    public MovieService() {
        this.movieDao = new MovieDao();
    }


    @Override
    public Movie save(Movie movie) {
        movieDao.save(movie);
        return movie;
    }

    @Override
    public Movie findById(String id) {
        return movieDao.findById(id);
    }

    @Override
    public List<Movie> findAll() {
        return movieDao.findAll();
    }

    @Override
    public void delete(String id) {
        movieDao.delete(id);
    }

    public List<Movie> findByTitle(String title) {
        return movieDao.findByTitle(title);
    }

    public List<Movie> findByGenre(String genre) {
        return movieDao.findByGenre(genre);
    }
}
