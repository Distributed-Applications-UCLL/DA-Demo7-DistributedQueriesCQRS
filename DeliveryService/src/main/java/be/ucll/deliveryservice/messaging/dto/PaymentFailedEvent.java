package be.ucll.deliveryservice.messaging.dto;

public record PaymentFailedEvent(
        String eventId,
        String sagaId,
        Long orderId,
        String reason
) {
}
