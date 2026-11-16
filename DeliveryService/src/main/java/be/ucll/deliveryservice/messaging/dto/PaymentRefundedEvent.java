package be.ucll.deliveryservice.messaging.dto;

public record PaymentRefundedEvent(
        String eventId,
        String sagaId,
        Long orderId
)  {}
