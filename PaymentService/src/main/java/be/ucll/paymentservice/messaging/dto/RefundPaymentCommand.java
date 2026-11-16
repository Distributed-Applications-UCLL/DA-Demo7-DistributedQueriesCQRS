package be.ucll.paymentservice.messaging.dto;

public record RefundPaymentCommand(
        String eventId,
        String sagaId,
        Long orderId
) {}
