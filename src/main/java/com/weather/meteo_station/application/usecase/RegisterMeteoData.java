package com.weather.meteo_station.application.usecase;

import com.weather.meteo_station.domain.MeteoData;
import com.weather.meteo_station.infrastructure.amqp.client.MeteoDataPublisher;
import com.weather.meteo_station.infrastructure.amqp.event.MeteoDataRegistrationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

@Named
public class RegisterMeteoData {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterMeteoData.class);
    private final MeteoDataPublisher meteoDataPublisher;

    public RegisterMeteoData(MeteoDataPublisher meteoDataPublisher) {
        this.meteoDataPublisher = meteoDataPublisher;
    }

    public void register(MeteoData meteoData) {
        LOGGER.info("Registering weather measure {}", meteoData);
        meteoDataPublisher.publish(map(meteoData));
    }

    private MeteoDataRegistrationEvent map(MeteoData meteoData) {
        return MeteoDataRegistrationEvent.builder()
                .withTimestamp(meteoData.getTimestamp())
                .withTemperature(meteoData.getTemperature())
                .withPressure(meteoData.getPressure())
                .withElevation(meteoData.getElevation())
                .build();
    }
}
