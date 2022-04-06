package com.weather.meteostation.infrastructure.amqp.client;

import com.weather.meteostation.domain.MeteoDataStatistics;
import com.weather.meteostation.infrastructure.amqp.event.GetTemperatureStatisticsEvent;
import com.weather.meteostation.infrastructure.amqp.event.TemperatureStatisticsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;

import javax.inject.Named;

import static java.util.Objects.nonNull;

@Named
public class GetTemperatureStatisticsPublisher {

    @Value("${amqp.exchange.temperature}")
    private String exchange;

    @Value("${amqp.routing-key.get-temperature-statistics}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(GetTemperatureStatisticsPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    public GetTemperatureStatisticsPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public MeteoDataStatistics publish(GetTemperatureStatisticsEvent getTemperatureStatisticsEvent) {
        LOGGER.info("Publishing get temperature statistics event {}", getTemperatureStatisticsEvent);
        TemperatureStatisticsEvent temperatureStatisticsEvent = rabbitTemplate.convertSendAndReceiveAsType(exchange, routingKey, getTemperatureStatisticsEvent, new ParameterizedTypeReference<TemperatureStatisticsEvent>() {});
        return nonNull(temperatureStatisticsEvent) ? MeteoDataStatistics.builder()
                .withCurrentTemperature(temperatureStatisticsEvent.getCurrentTemperature())
                .withAvgTemperature(temperatureStatisticsEvent.getAvgTemperature())
                .withMaxTemperature(temperatureStatisticsEvent.getMaxTemperature())
                .withMinTemperature(temperatureStatisticsEvent.getMinTemperature())
                .build() : null;
    }
}
