package org.project.service;

import org.project.model.PaymentIntent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentIntentService {
    private static ConcurrentHashMap<String, PaymentIntent> paymentIntents = new ConcurrentHashMap<>();

    public PaymentIntent getPaymentIntent(String paymentIntentId) {
        return paymentIntents.get(paymentIntentId);
    }

    public PaymentIntent createPaymentIntent(PaymentIntent paymentIntent) {
        paymentIntents.put(paymentIntent.getId(), paymentIntent);
        return paymentIntent;
    }

    public List<PaymentIntent> getAll() {
        return new ArrayList<>(paymentIntents.values());
    }

    public void update(PaymentIntent paymentIntent) {
        paymentIntents.put(paymentIntent.getId(), paymentIntent);
    }

    public void delete(String paymentIntentId) {
        paymentIntents.remove(paymentIntentId);
    }
}
