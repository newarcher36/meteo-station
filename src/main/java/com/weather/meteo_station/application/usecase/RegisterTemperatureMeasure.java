package com.weather.meteo_station.application.usecase;

import com.weather.meteo_station.domain.WeatherMeasure;
import com.weather.meteo_station.infrastructure.amqp.client.TemperatureMeasurePublisher;
import com.weather.meteo_station.infrastructure.amqp.event.WeatherMeasureEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

@Named
public class RegisterTemperatureMeasure {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterTemperatureMeasure.class);
    private final TemperatureMeasurePublisher temperatureMeasurePublisher;

    public RegisterTemperatureMeasure(TemperatureMeasurePublisher temperatureMeasurePublisher) {
        this.temperatureMeasurePublisher = temperatureMeasurePublisher;
    }

    public void registerTemperature(WeatherMeasure weatherMeasure) {
        LOGGER.info("Registering weather measure {}", weatherMeasure);
        temperatureMeasurePublisher.publish(map(weatherMeasure));
    }

    private WeatherMeasureEvent map(WeatherMeasure weatherMeasure) {
        return WeatherMeasureEvent.builder()
                .withTimestamp(weatherMeasure.getTimestamp())
                .withTemperature(weatherMeasure.getTemperature())
                .withPressure(weatherMeasure.getPressure())
                .withElevation(weatherMeasure.getElevation())
                .build();
    }
}
