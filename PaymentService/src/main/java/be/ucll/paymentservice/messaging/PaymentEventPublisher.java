package be.ucll.paymentservice.messaging;

import be.ucll.paymentservice.messaging.dto.PaymentFailedEvent;
import be.ucll.paymentservice.messaging.dto.PaymentRefundedEvent;
import be.ucll.paymentservice.messaging.dto.PaymentSucceededEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public PaymentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishPaymentSucceeded(PaymentSucceededEvent event) {

        rabbitTemplate.convertAndSend(
                "orders.saga.exchange",
                "payment.succeeded",
                event
        );
    }

    public void publishPaymentFailed(PaymentFailedEvent event) {

        rabbitTemplate.convertAndSend(
                "orders.saga.exchange",
                "payment.failed",
                event
        );
    }

    public void publishPaymentRefunded(PaymentRefundedEvent event) {

        rabbitTemplate.convertAndSend(
                "orders.saga.exchange",
                "payment.refunded",
                event
        );
    }
}
