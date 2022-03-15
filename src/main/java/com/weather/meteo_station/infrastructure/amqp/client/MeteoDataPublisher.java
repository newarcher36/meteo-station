package com.weather.meteo_station.infrastructure.amqp.client;

import com.weather.meteo_station.infrastructure.amqp.event.MeteoDataRegistrationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.inject.Named;

@Named
public class MeteoDataPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeteoDataPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    public MeteoDataPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(MeteoDataRegistrationEvent meteoDataRegistrationEvent) {
        rabbitTemplate.convertAndSend(meteoDataRegistrationEvent);
        LOGGER.info("Sending user event message [{}] with exchange [{}] and routing-key [{}]", meteoDataRegistrationEvent, rabbitTemplate.getExchange(), rabbitTemplate.getRoutingKey());
    }
}
