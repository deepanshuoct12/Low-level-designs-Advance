package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.project.observer.IObserver;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements IObserver {
    private String groupId;
    private String name;
    private String email;

    @Override
    public void update(Expense expense) {
        System.out.println("User " + name + " received notification for expense " + expense.getAmount());
    }
}
