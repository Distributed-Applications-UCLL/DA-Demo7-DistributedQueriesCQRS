package be.ucll.orderservice.messaging.dto;

public record DeliveryFailedEvent(
        String eventId,
        String sagaId,
        Long orderId,
        String reason
){}
