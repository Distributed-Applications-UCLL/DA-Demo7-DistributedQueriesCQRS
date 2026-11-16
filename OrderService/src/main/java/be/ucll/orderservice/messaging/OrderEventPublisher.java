package be.ucll.orderservice.messaging;

import be.ucll.orderservice.messaging.dto.CreateDeliveryCommand;
import be.ucll.orderservice.messaging.dto.OrderCompletedEvent;
import be.ucll.orderservice.messaging.dto.OrderCreatedEvent;
import be.ucll.orderservice.messaging.dto.RefundPaymentCommand;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public OrderEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishOrderCreated(OrderCreatedEvent event) {
        System.out.println("order created");
        rabbitTemplate.convertAndSend(
                SagaMessagingConfig.ORDER_EXCHANGE,
                "payment.order.created",
                event
        );
    }

    public void publishCreateDelivery(CreateDeliveryCommand command) {
        System.out.println("create delivery");
        rabbitTemplate.convertAndSend(
                SagaMessagingConfig.SAGA_EXCHANGE,
                "delivery.create",
                command
        );
    }

    public void publishRefundPayment(RefundPaymentCommand command) {
        System.out.println("refund please");
        rabbitTemplate.convertAndSend(
                SagaMessagingConfig.SAGA_EXCHANGE,
                "payment.refund",
                command
        );
    }

    public void publishOrderCompleted(OrderCompletedEvent event) {
        System.out.println("order completed");
        rabbitTemplate.convertAndSend(
                SagaMessagingConfig.SAGA_EXCHANGE,
                "order.complete",
                event
        );
    }
}
