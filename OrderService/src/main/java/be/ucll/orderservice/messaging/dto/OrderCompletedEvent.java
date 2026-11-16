package be.ucll.orderservice.messaging.dto;

import be.ucll.orderservice.dto.OrderDTO;

public record OrderCompletedEvent(
        String eventId,
        OrderDTO orderDTO
) {
}
