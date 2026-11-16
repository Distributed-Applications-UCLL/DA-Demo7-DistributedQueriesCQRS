package be.ucll.orderservice.messaging;


import be.ucll.orderservice.business.OrderSagaOrchestrator;
import be.ucll.orderservice.messaging.dto.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SagaResultListener {

    private final OrderSagaOrchestrator orchestrator;

    public SagaResultListener(OrderSagaOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @RabbitListener(
            queues = SagaMessagingConfig.PAYMENT_SUCCEEDED_QUEUE)
    public void onPaymentSucceeded(PaymentSucceededEvent event) {
        orchestrator.handlePaymentSucceeded(event);
    }

    @RabbitListener(
            queues = SagaMessagingConfig.PAYMENT_FAILED_QUEUE)
    public void onPaymentFailed(PaymentFailedEvent event) {
        orchestrator.handlePaymentFailed(event);
    }

    @RabbitListener(
            queues = SagaMessagingConfig.DELIVERY_CREATED_QUEUE)
    public void onDeliveryCreated(DeliveryCreatedEvent event) {
        orchestrator.handleDeliveryCreated(event);
    }

    @RabbitListener(
            queues = SagaMessagingConfig.DELIVERY_FAILED_QUEUE)
    public void onDeliveryFailed(DeliveryFailedEvent event) {
        orchestrator.handleDeliveryFailed(event);
    }

    @RabbitListener(
            queues = SagaMessagingConfig.PAYMENT_REFUNDED_QUEUE)
    public void onPaymentRefunded(PaymentRefundedEvent event) {
        orchestrator.handlePaymentRefunded(event);
    }
}
