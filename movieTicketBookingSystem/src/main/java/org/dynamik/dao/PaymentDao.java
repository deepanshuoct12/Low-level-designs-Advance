package org.dynamik.dao;

import org.dynamik.model.Payment;
import org.dynamik.enums.PaymentStatus;

import java.util.*;

public class PaymentDao {
    private static final Map<String, Payment> payments = new HashMap<>();

    public void save(Payment payment) {
        if (payment.getId() == null) {
            payment.setId(UUID.randomUUID().toString());
        }
        payments.put(payment.getId(), payment);
    }

    public Payment findById(String id) {
        return payments.get(id);
    }

    public List<Payment> findAll() {
        return new ArrayList<>(payments.values());
    }

    public void delete(String id) {
        payments.remove(id);
    }

    public Payment findByBookingId(String bookingId) {
        return payments.values().stream()
                .filter(payment -> payment.getBookingId().equals(bookingId)).findFirst().orElse(null);
    }

    public List<Payment> findByStatus(PaymentStatus status) {
        return payments.values().stream()
                .filter(payment -> payment.getStatus() == status)
                .toList();
    }
}
