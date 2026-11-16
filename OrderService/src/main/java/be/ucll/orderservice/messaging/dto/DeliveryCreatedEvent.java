package be.ucll.orderservice.messaging.dto;

public record DeliveryCreatedEvent(
        String eventId,
        String sagaId,
        Long orderId,
        String deliveryId
){}
