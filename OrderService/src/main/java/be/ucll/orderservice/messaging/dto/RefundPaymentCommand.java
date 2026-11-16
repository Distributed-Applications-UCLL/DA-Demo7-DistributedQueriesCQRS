package be.ucll.orderservice.messaging.dto;

public record RefundPaymentCommand(
        String eventId,
        String sagaId,
        Long orderId
) {}
