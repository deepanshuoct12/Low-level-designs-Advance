package org.dynamik.observer;

import org.dynamik.model.Movie;
import org.dynamik.model.User;

public class UserObserver implements MovieObserver {
    private User user;

    public UserObserver(User user) {
        this.user = user;
    }

    @Override
    public void update(Movie movie) {
        System.out.println("User: " + user.getName() + "please watch" + movie.getTitle() + " is now available");
    }
}
