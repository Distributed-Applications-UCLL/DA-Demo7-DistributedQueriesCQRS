package be.ucll.deliveryservice.messaging.dto;

import java.math.BigDecimal;

public record OrderCreatedEvent(
        String eventId,
        String sagaId,
        Long orderId,
        Long customerId,
        BigDecimal total
) {}