package be.ucll.orderservice.messaging.dto;

public record PaymentFailedEvent(
        String eventId,
        String sagaId,
        Long orderId,
        String reason
) {
}
