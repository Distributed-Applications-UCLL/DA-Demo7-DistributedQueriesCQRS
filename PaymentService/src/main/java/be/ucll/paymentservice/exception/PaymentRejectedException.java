package be.ucll.paymentservice.exception;

public class PaymentRejectedException extends Exception {

    public PaymentRejectedException(String message) {
        super(message);
    }
}
