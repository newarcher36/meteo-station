package com.weather.meteo_station.infrastructure.amqp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchangeName;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public MessageConverter converter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory, RabbitProperties rabbitProperties, ObjectMapper mapper){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setMandatory(true);

        RabbitTemplateConfigurer rabbitTemplateConfigurer = new RabbitTemplateConfigurer(rabbitProperties);
        rabbitTemplate.setMessageConverter(converter(mapper));
        rabbitTemplateConfigurer.configure(rabbitTemplate, connectionFactory);

        return rabbitTemplate;
    }
}