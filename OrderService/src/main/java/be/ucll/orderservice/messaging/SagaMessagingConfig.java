package be.ucll.orderservice.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SagaMessagingConfig {

    public static final String ORDER_EXCHANGE = "orders.exchange";
    public static final String SAGA_EXCHANGE = "orders.saga.exchange";

    public static final String PAYMENT_SUCCEEDED_QUEUE =
            "orders.payment.succeeded.queue";

    public static final String PAYMENT_FAILED_QUEUE =
            "orders.payment.failed.queue";

    public static final String DELIVERY_CREATED_QUEUE =
            "orders.delivery.created.queue";

    public static final String DELIVERY_FAILED_QUEUE =
            "orders.delivery.failed.queue";

    public static final String PAYMENT_REFUNDED_QUEUE =
            "orders.payment.refunded.queue";

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(ORDER_EXCHANGE);
    }

    @Bean
    public TopicExchange sagaExchange() {
        return new TopicExchange(SAGA_EXCHANGE);
    }

    @Bean
    public Queue paymentSucceededQueue() {
        return new Queue(PAYMENT_SUCCEEDED_QUEUE);
    }

    @Bean
    public Queue paymentFailedQueue() {
        return new Queue(PAYMENT_FAILED_QUEUE);
    }

    @Bean
    public Queue deliveryCreatedQueue() {
        return new Queue(DELIVERY_CREATED_QUEUE);
    }

    @Bean
    public Queue deliveryFailedQueue() {
        return new Queue(DELIVERY_FAILED_QUEUE);
    }

    @Bean
    public Queue paymentRefundedQueue() {
        return new Queue(PAYMENT_REFUNDED_QUEUE);
    }

    @Bean
    public Binding paymentSucceededBinding() {
        return BindingBuilder.bind(paymentSucceededQueue())
                .to(sagaExchange())
                .with("payment.succeeded");
    }

    @Bean
    public Binding paymentFailedBinding() {
        return BindingBuilder.bind(paymentFailedQueue())
                .to(sagaExchange())
                .with("payment.failed");
    }

    @Bean
    public Binding deliveryCreatedBinding() {
        return BindingBuilder.bind(deliveryCreatedQueue())
                .to(sagaExchange())
                .with("delivery.created");
    }

    @Bean
    public Binding deliveryFailedBinding() {
        return BindingBuilder.bind(deliveryFailedQueue())
                .to(sagaExchange())
                .with("delivery.failed");
    }

    @Bean
    public Binding paymentRefundedBinding() {
        return BindingBuilder.bind(paymentRefundedQueue())
                .to(sagaExchange())
                .with("payment.refunded");
    }

    @Bean
    public JacksonJsonMessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
