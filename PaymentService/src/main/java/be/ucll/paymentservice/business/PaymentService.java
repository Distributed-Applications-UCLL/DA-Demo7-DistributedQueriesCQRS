package be.ucll.paymentservice.business;

import be.ucll.paymentservice.exception.PaymentRejectedException;
import be.ucll.paymentservice.persistence.Payment;
import be.ucll.paymentservice.persistence.PaymentRepository;
import be.ucll.paymentservice.persistence.PaymentStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found: " + id));
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment charge(
            String sagaId,
            Long orderId,
            Long customerId,
            BigDecimal amount) throws PaymentRejectedException {

        var existing = paymentRepository.findById(orderId);

        // Prevent duplicate charges.
        if (existing.isPresent()) {

            Payment payment = existing.get();

            if (payment.getStatus() == PaymentStatus.PAID) {
                return payment;
            }

            throw new IllegalStateException(
                    "Payment has already been refunded"
            );
        }

        // Demonstration: reject payments above 1000.
        if (amount.compareTo(new BigDecimal("1000")) > 0) {
            throw new PaymentRejectedException(
                    "Payment declined: amount exceeds 1000"
            );
        }

        // Simulate successful payment.
        // A real implementation would call a payment provider here.
        Payment payment = new Payment();

        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.PAID);

        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment refund(String sagaId, Long orderId) {

        System.out.println("refunding payment with id "+ orderId);
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Payment not found"
                        )
                );

        // Prevent duplicate refunds.
        if (payment.getStatus() == PaymentStatus.REFUNDED) {
            return payment;
        }

        // Simulate refund.
        // A real implementation would call the payment provider here.
        payment.setStatus(PaymentStatus.REFUNDED);

        return paymentRepository.save(payment);
    }


}
