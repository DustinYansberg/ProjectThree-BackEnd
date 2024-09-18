package com.skillstorm.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitMQConfig
{
    public static final String QUEUE_NAME = "entitlement-queue";
    public static final String EXCHANGE_NAME = "entitlement-exchange";
    public static final String ROUTING_KEY = "entitlement.routing.key";

    @Bean
    public Queue queue()
    {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange exchange()
    {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange)
    {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public SimpleMessageConverter messageConverter() {
        // Create a SimpleMessageConverter
        SimpleMessageConverter converter = new SimpleMessageConverter();

        // Set allowed list patterns (for security)
        converter.setAllowedListPatterns(List.of("com.skillstorm.models.PermissionUpdateMessage", "java.util.*"));

        return converter;
    }
}
