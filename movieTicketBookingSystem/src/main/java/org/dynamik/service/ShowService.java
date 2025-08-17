package org.dynamik.service;

import org.dynamik.dao.ShowDao;
import org.dynamik.model.Show;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ShowService implements BaseService<Show, String> {
    private final ShowDao showDao = new ShowDao();

    @Override
    public Show save(Show show) {
        showDao.save(show);
        return show;
    }

    @Override
    public Show findById(String id) {
        return showDao.findById(id);
    }

    @Override
    public List<Show> findAll() {
        return showDao.findAll();
    }

    @Override
    public void delete(String id) {
        showDao.delete(id);
    }

    public List<Show> findShowsByMovieId(String movieId) {
        return showDao.findByMovieId(movieId);
    }

    public List<Show> findShowsByTheatreId(String theatreId) {
        return showDao.findByTheatreId(theatreId);
    }

    public List<Show> findShowsBetweenDates(LocalDateTime start, LocalDateTime end) {
        return showDao.findAll().stream()
                .filter(show -> !show.getStartTime().isBefore(start) && !show.getStartTime().isAfter(end))
                .collect(Collectors.toList());
    }
}
