package org.dynamik.model;

import lombok.Data;
import org.dynamik.observer.IObserver;

@Data
public class Subscriber implements IObserver {
    private String id;
    private Long updatedAt;
    private Long createdAt;
    private String name;

    @Override
    public void update(Message message) {
     System.out.println("Subscriber " + name + " has received a message: " + message.getContent());
    }
}
