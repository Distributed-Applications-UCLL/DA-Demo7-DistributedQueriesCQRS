package be.ucll.deliveryservice.messaging.dto;

public record PaymentSucceededEvent(
        String eventId,
        String sagaId,
        Long orderId,
        String paymentId
) {
}
