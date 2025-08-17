package org.dynamik.service;

import org.dynamik.dao.PaymentDao;
import org.dynamik.enums.PaymentStatus;
import org.dynamik.model.Payment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentService implements BaseService<Payment, String> {
    private final PaymentDao paymentDao = new PaymentDao();

    @Override
    public Payment save(Payment payment) {
        paymentDao.save(payment);
        return payment;
    }

    @Override
    public Payment findById(String id) {
        return paymentDao.findById(id);
    }

    @Override
    public List<Payment> findAll() {
        return paymentDao.findAll();
    }

    @Override
    public void delete(String id) {
        paymentDao.delete(id);
    }

    public Payment findPaymentsByBookingId(String bookingId) {
        return paymentDao.findByBookingId(bookingId);
    }

    public List<Payment> findPaymentsByStatus(PaymentStatus status) {
        return paymentDao.findAll().stream()
                .filter(payment -> payment.getStatus() == status)
                .collect(Collectors.toList());
    }

    public Payment findByBookingId(String bookingId) {
        return paymentDao.findByBookingId(bookingId);
    }

//    public List<Payment> findPaymentsBetweenDates(LocalDateTime start, LocalDateTime end) {
//        return paymentDao.findAll().stream()
//                .filter(payment -> !payment.getPaymentDate().isBefore(start) && !payment.getPaymentDate().isAfter(end))
//                .collect(Collectors.toList());
//    }

//    public boolean processRefund(String paymentId) {
//        Payment payment = findById(paymentId);
//        if (payment != null && payment.getStatus() == PaymentStatus.COMPLETED) {
//            payment.setStatus(PaymentStatus.REFUNDED);
//            payment.setLastUpdated(LocalDateTime.now());
//            save(payment);
//            return true;
//        }
//        return false;
//    }
}
