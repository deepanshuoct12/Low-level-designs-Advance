package org.dynamik.service;

import org.dynamik.dao.TheatreDao;
import org.dynamik.model.Theatre;

import java.util.List;

public class TheatreService implements BaseService<Theatre, String> {
    private final TheatreDao theatreDao = new TheatreDao();

    @Override
    public Theatre save(Theatre theatre) {
        theatreDao.save(theatre);
        return theatre;
    }

    @Override
    public Theatre findById(String id) {
        return theatreDao.findById(id);
    }

    @Override
    public List<Theatre> findAll() {
        return theatreDao.findAll();
    }

    @Override
    public void delete(String id) {
        theatreDao.delete(id);
    }

    public List<Theatre> findByName(String name) {
        return theatreDao.findAll().stream()
                .filter(theatre -> theatre.getName().equalsIgnoreCase(name))
                .collect(java.util.stream.Collectors.toList());
    }
}
