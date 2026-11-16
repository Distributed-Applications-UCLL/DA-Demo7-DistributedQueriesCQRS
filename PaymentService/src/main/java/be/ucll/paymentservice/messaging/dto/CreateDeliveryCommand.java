package be.ucll.paymentservice.messaging.dto;

public record CreateDeliveryCommand(
        String eventId,
        String sagaId,
        Long orderId
)  {}
