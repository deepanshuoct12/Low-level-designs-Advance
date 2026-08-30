package org.project.model;

import lombok.Data;
import org.project.enums.UserStatus;
import org.project.observer.IObserver;

@Data
public class User extends BaseEntity implements IObserver {
    private String username;
    private String email;
    private UserStatus status;

    @Override
    public void update(Answer answer) {
        System.out.println(answer.getAuthorId() + " posted answer : " + answer.getBody());
    }
}
