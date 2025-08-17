package org.dynamik.observer;

import org.dynamik.model.Movie;

public interface MovieObserver {
    void update(Movie movie);
}
