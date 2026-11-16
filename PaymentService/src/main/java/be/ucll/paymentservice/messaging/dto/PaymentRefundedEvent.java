package be.ucll.paymentservice.messaging.dto;

public record PaymentRefundedEvent(
        String eventId,
        String sagaId,
        Long orderId
)  {}
