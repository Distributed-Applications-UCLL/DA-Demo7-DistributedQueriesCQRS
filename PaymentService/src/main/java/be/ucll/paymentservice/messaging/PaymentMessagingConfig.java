package be.ucll.paymentservice.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentMessagingConfig {

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange("orders.exchange");
    }

    @Bean
    public TopicExchange sagaExchange() {
        return new TopicExchange("orders.saga.exchange");
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue("payment.order.created.queue");
    }

    @Bean
    public Queue refundPaymentQueue() {
        return new Queue("payment.refund.queue");
    }

    @Bean
    public Binding orderCreatedBinding() {
        return BindingBuilder.bind(orderCreatedQueue())
                .to(orderExchange())
                .with("payment.order.created");
    }

    @Bean
    public Binding refundPaymentBinding() {
        return BindingBuilder.bind(refundPaymentQueue())
                .to(sagaExchange())
                .with("payment.refund");
    }

    @Bean
    public JacksonJsonMessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
