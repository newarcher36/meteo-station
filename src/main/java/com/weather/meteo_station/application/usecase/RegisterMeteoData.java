package com.weather.meteo_station.application.usecase;

import com.weather.meteo_station.domain.MeteoData;
import com.weather.meteo_station.infrastructure.amqp.client.TemperatureMeasurePublisher;
import com.weather.meteo_station.infrastructure.amqp.event.WeatherMeasureEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

@Named
public class RegisterMeteoData {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterMeteoData.class);
    private final TemperatureMeasurePublisher temperatureMeasurePublisher;

    public RegisterMeteoData(TemperatureMeasurePublisher temperatureMeasurePublisher) {
        this.temperatureMeasurePublisher = temperatureMeasurePublisher;
    }

    public void register(MeteoData meteoData) {
        LOGGER.info("Registering weather measure {}", meteoData);
        temperatureMeasurePublisher.publish(map(meteoData));
    }

    private WeatherMeasureEvent map(MeteoData meteoData) {
        return WeatherMeasureEvent.builder()
                .withTimestamp(meteoData.getTimestamp())
                .withTemperature(meteoData.getTemperature())
                .withPressure(meteoData.getPressure())
                .withElevation(meteoData.getElevation())
                .build();
    }
}
