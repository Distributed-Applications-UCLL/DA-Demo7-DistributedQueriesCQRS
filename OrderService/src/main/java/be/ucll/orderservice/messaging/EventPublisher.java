package be.ucll.orderservice.messaging;

import be.ucll.orderservice.messaging.dto.CreateDeliveryCommand;
import be.ucll.orderservice.messaging.dto.OrderCreatedEvent;
import be.ucll.orderservice.messaging.dto.RefundPaymentCommand;

public interface EventPublisher {
    void publishOrderCreated(OrderCreatedEvent event);
    void publishCreateDelivery(CreateDeliveryCommand command);
    void publishRefundPayment(RefundPaymentCommand command);
}
