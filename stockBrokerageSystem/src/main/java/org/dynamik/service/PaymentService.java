package org.dynamik.service;

import lombok.RequiredArgsConstructor;
import org.dynamik.dao.PaymentDao;
import org.dynamik.model.Payment;
import org.dynamik.enums.PaymentState;

import java.util.List;


public class PaymentService {
    
    private final PaymentDao paymentDao = new PaymentDao();
    
    public Payment save(Payment payment) {
        return paymentDao.save(payment);
    }

    public Payment findById(String id) {
        return paymentDao.getById(id);
    }

    public List<Payment> findAll() {
        return paymentDao.getAll();
    }

    public void deleteById(String id) {
        paymentDao.delete(id);
    }

    public Payment update(Payment payment) {
        return paymentDao.save(payment);
    }
    
    public Payment createPayment(String orderId, Long amount) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setPaymentState(PaymentState.PENDING);
        return paymentDao.save(payment);
    }

    public boolean processPayment(String paymentId) {
        return paymentDao.updatePaymentState(paymentId, PaymentState.SUCCESS);
    }

    public Payment findByOrderId(String orderId) {
        return paymentDao.findByOrderId(orderId);
    }
    
    public boolean updatePaymentState(String paymentId, PaymentState state) {
        return paymentDao.updatePaymentState(paymentId, state);
    }
}
