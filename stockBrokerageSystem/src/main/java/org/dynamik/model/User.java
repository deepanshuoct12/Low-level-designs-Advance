package org.dynamik.model;

import lombok.Data;
import org.dynamik.observer.IObserver;

@Data
public class User extends AbstractEntity implements IObserver {
    private String name;
    private String email;

    @Override
    public void update(AbstractEntity entity) {
        String message = switch(entity) {
            case Payment payment ->
                    "User " + name + " payment status for order " + payment.getOrderId() + " is " + payment.getPaymentState();
            case Stock stock ->
                    "User " + name + " stock price for " + stock.getName() + " is " + stock.getPrice();
            default ->
                    "Unknown update type: " + entity.getClass().getSimpleName();
        };

        System.out.println(message);
    }

}
