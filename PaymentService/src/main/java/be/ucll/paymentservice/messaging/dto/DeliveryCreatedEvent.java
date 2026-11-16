package be.ucll.paymentservice.messaging.dto;

public record DeliveryCreatedEvent(
        String eventId,
        String sagaId,
        Long orderId,
        String deliveryId
){}
