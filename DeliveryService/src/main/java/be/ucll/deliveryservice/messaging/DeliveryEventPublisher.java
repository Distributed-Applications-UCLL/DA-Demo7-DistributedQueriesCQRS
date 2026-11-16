package be.ucll.deliveryservice.messaging;

import be.ucll.deliveryservice.messaging.dto.DeliveryCreatedEvent;
import be.ucll.deliveryservice.messaging.dto.DeliveryFailedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class DeliveryEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public DeliveryEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishDeliveryCreated(DeliveryCreatedEvent event) {

        rabbitTemplate.convertAndSend(
                "orders.saga.exchange",
                "delivery.created",
                event
        );
    }

    public void publishDeliveryFailed(DeliveryFailedEvent event) {

        rabbitTemplate.convertAndSend(
                "orders.saga.exchange",
                "delivery.failed",
                event
        );
    }
}
