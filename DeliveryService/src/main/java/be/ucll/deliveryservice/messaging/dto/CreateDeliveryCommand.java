package be.ucll.deliveryservice.messaging.dto;

public record CreateDeliveryCommand(
        String eventId,
        String sagaId,
        Long orderId
)  {}
