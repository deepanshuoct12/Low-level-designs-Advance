package org.dynamik.dao;

import org.dynamik.enums.PaymentState;
import org.dynamik.model.Payment;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentDao implements IBaseDao<Payment, String> {
    private final static Map<String, Payment> payments = new ConcurrentHashMap<>();

    @Override
    public Payment save(Payment payment) {
        return payments.put(payment.getId(), payment);
    }

    @Override
    public Payment getById(String s) {
        return payments.get(s);
    }

    @Override
    public void update(Payment payment) {
     payments.put(payment.getId(), payment);
    }

    @Override
    public void delete(String id) {
        payments.remove(id);
    }

    @Override
    public List<Payment> getAll() {
        return payments.values().stream().toList();
    }

    public boolean updatePaymentState(String paymentId, PaymentState state) {
         payments.get(paymentId).setPaymentState(state);
         return true;
    }

    public Payment findByOrderId(String orderId) {
        return payments.values().stream()
                .filter(payment -> payment.getOrderId().equals(orderId)).findFirst().orElse(null);
    }
}
