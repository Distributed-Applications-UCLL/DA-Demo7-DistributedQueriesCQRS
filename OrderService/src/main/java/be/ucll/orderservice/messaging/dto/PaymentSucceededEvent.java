package be.ucll.orderservice.messaging.dto;

public record PaymentSucceededEvent(
        String eventId,
        String sagaId,
        Long orderId,
        String paymentId
) {
}
