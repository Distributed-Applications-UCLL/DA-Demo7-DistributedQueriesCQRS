package be.ucll.deliveryservice.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeliveryMessagingConfig {

    @Bean
    public TopicExchange sagaExchange() {
        return new TopicExchange("orders.saga.exchange");
    }

    @Bean
    public Queue createDeliveryQueue() {
        return new Queue("delivery.create.queue");
    }

    @Bean
    public Binding createDeliveryBinding() {
        return BindingBuilder.bind(createDeliveryQueue())
                .to(sagaExchange())
                .with("delivery.create");
    }

    @Bean
    public JacksonJsonMessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
