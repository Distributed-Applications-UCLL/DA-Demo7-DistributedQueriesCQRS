package be.ucll.orderhistoryservice.messaging;

import be.ucll.orderhistoryservice.dto.OrderDTO;

public record OrderCompletedEvent(
        String eventId,
        OrderDTO orderDTO
) {
}
