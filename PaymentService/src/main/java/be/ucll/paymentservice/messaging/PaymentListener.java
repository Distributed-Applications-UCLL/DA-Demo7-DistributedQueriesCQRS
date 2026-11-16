package be.ucll.paymentservice.messaging;

import be.ucll.paymentservice.business.PaymentService;
import be.ucll.paymentservice.exception.PaymentRejectedException;
import be.ucll.paymentservice.messaging.dto.*;
import be.ucll.paymentservice.persistence.Payment;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentListener {

    private final PaymentService paymentService;
    private final PaymentEventPublisher publisher;

    public PaymentListener(
            PaymentService paymentService,
            PaymentEventPublisher publisher) {

        this.paymentService = paymentService;
        this.publisher = publisher;
    }

    @RabbitListener(queues = "payment.order.created.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {

        try {

            Payment payment = paymentService.charge(
                    event.sagaId(),
                    event.orderId(),
                    event.customerId(),
                    event.total()
            );

            System.out.println("payment succeeded");
            publisher.publishPaymentSucceeded(
                    new PaymentSucceededEvent(
                            UUID.randomUUID().toString(),
                            event.sagaId(),
                            event.orderId(),
                            payment.getId().toString()
                    )
            );

        } catch (PaymentRejectedException exception) {

            System.out.println("payment failed");
            publisher.publishPaymentFailed(
                    new PaymentFailedEvent(
                            UUID.randomUUID().toString(),
                            event.sagaId(),
                            event.orderId(),
                            exception.getMessage()
                    )
            );
        }
    }

    @RabbitListener(queues = "payment.refund.queue")
    public void handleRefund(RefundPaymentCommand command) {

        paymentService.refund(
                command.sagaId(),
                command.orderId()
        );
        System.out.println("refunding payment");
        publisher.publishPaymentRefunded(
                new PaymentRefundedEvent(
                        UUID.randomUUID().toString(),
                        command.sagaId(),
                        command.orderId()
                )
        );
    }
}