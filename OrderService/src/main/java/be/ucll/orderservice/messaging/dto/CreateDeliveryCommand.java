package be.ucll.orderservice.messaging.dto;

public record CreateDeliveryCommand(
        String eventId,
        String sagaId,
        Long orderId
)  {}
