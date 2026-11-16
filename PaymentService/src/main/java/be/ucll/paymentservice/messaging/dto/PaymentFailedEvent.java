package be.ucll.paymentservice.messaging.dto;

public record PaymentFailedEvent(
        String eventId,
        String sagaId,
        Long orderId,
        String reason
) {
}
