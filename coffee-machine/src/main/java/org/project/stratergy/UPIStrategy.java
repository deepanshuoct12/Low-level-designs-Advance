package org.project.stratergy;

public class UPIStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing UPI payment of amount: " + amount);
        // Simulate UPI payment processing
        return true;
    }
}
