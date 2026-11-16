package be.ucll.deliveryservice.messaging.dto;

public record RefundPaymentCommand(
        String eventId,
        String sagaId,
        Long orderId
) {}
