package org.dynamik.model;

import lombok.Data;
import org.dynamik.observer.IObserver;

@Data
public class User implements IObserver {
    private String id;
    private String username;
    private String email;

    @Override
    public void update(Bid bid) {
        System.out.println("User " + bid.getUserId() + " placed a highest bid" + bid.getId() + " with amount " + bid.getAmount());
    }
}
