package com.skillstorm.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitMQConfig
{
    public static final String EXCHANGE_NAME = "entitlement-exchange";
    public static final String ROUTING_KEY = "entitlement.routing.key";

    @Bean
    public TopicExchange exchange()
    {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public SimpleMessageConverter messageConverter()
    {
        SimpleMessageConverter converter = new SimpleMessageConverter();

        converter.setAllowedListPatterns(List.of("com.skillstorm.models.PermissionUpdateMessage", "java.util.*"));

        return converter;
    }
}
