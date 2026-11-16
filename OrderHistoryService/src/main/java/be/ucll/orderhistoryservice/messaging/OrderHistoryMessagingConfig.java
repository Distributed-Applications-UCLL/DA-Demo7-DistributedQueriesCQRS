package be.ucll.orderhistoryservice.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderHistoryMessagingConfig {

    public static final String SAGA_EXCHANGE = "orders.saga.exchange";
    public static final String ORDER_COMPLETED_QUEUE="order.completed.queue";
    @Bean
    public TopicExchange sagaExchange() {
        return new TopicExchange(SAGA_EXCHANGE);
    }

    @Bean
    public Queue createOrderCompletedQueue() {
        return new Queue(ORDER_COMPLETED_QUEUE);
    }

    @Bean
    public Binding createOrderCompletedBinding() {
        return BindingBuilder.bind(createOrderCompletedQueue())
                .to(sagaExchange())
                .with("order.complete");
    }

    @Bean
    public JacksonJsonMessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }


}
