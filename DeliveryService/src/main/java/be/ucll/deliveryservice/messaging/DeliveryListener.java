package be.ucll.deliveryservice.messaging;

import be.ucll.deliveryservice.business.DeliveryService;
import be.ucll.deliveryservice.exception.DeliveryRejectedException;
import be.ucll.deliveryservice.messaging.dto.CreateDeliveryCommand;
import be.ucll.deliveryservice.messaging.dto.DeliveryCreatedEvent;
import be.ucll.deliveryservice.messaging.dto.DeliveryFailedEvent;
import be.ucll.deliveryservice.messaging.dto.OrderCreatedEvent;
import be.ucll.deliveryservice.persistence.Delivery;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeliveryListener {

    private final DeliveryService deliveryService;
    private final DeliveryEventPublisher publisher;

    public DeliveryListener(
            DeliveryService deliveryService,
            DeliveryEventPublisher publisher) {

        this.deliveryService = deliveryService;
        this.publisher = publisher;
    }

    @RabbitListener(queues = "delivery.create.queue")
    public void handleCreateDelivery(CreateDeliveryCommand command) {

        try {

            Delivery delivery = deliveryService.createDelivery(
                    command.sagaId(),
                    command.orderId()
            );
            System.out.println("delivery succeeded");

            publisher.publishDeliveryCreated(
                    new DeliveryCreatedEvent(
                            UUID.randomUUID().toString(),
                            command.sagaId(),
                            command.orderId(),
                            delivery.getId().toString()
                    )
            );

        } catch (DeliveryRejectedException exception) {
            System.out.println("delivery failed");
            publisher.publishDeliveryFailed(
                    new DeliveryFailedEvent(
                            UUID.randomUUID().toString(),
                            command.sagaId(),
                            command.orderId(),
                            exception.getMessage()
                    )
            );
        }
    }
}