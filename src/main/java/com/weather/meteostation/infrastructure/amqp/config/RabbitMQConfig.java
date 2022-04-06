package com.weather.meteostation.infrastructure.amqp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

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