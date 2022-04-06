package com.weather.meteostation.infrastructure.amqp.client;

import com.weather.meteostation.infrastructure.amqp.event.SaveTemperatureDataEvent;
import com.weather.meteostation.infrastructure.amqp.event.TemperatureDataEventSaved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;

import javax.inject.Named;

@Named
public class SaveTemperatureEventPublisher {

    @Value("${amqp.exchange.temperature}")
    private String exchange;

    @Value("${amqp.routing-key.save-temperature}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveTemperatureEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    public SaveTemperatureEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public TemperatureDataEventSaved publish(SaveTemperatureDataEvent saveTemperatureDataEvent) {
        LOGGER.info("Sending save temperature event message [{}] with exchange [{}] and routing-key [{}]", saveTemperatureDataEvent, rabbitTemplate.getExchange(), rabbitTemplate.getRoutingKey());
        return rabbitTemplate.convertSendAndReceiveAsType(exchange, routingKey, saveTemperatureDataEvent, new ParameterizedTypeReference<TemperatureDataEventSaved>() {});
    }
}