package be.ucll.orderservice.messaging.dto;

public record PaymentRefundedEvent(
        String eventId,
        String sagaId,
        Long orderId
)  {}
