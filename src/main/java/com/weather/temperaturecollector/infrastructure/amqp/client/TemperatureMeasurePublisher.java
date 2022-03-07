package com.weather.temperaturecollector.infrastructure.amqp.client;

import com.weather.temperaturecollector.infrastructure.amqp.event.WeatherMeasureEvent;

import javax.inject.Named;

@Named
public class TemperatureMeasurePublisher {

    public void publish(WeatherMeasureEvent weatherMeasureEvent) {

    }
}
