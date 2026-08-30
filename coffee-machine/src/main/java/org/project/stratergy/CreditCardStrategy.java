package org.project.stratergy;

public class CreditCardStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing Credit Card payment of amount: " + amount);
        // Simulate Credit Card payment processing
        return true;
    }
}
