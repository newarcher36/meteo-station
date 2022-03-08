package com.weather.meteo_station.infrastructure.amqp.client;

import com.weather.meteo_station.infrastructure.amqp.event.WeatherMeasureEvent;

import javax.inject.Named;

@Named
public class TemperatureMeasurePublisher {

    public void publish(WeatherMeasureEvent weatherMeasureEvent) {

    }
}
