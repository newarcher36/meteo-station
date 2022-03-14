package com.weather.meteo_station.infrastructure.amqp.client;

import com.weather.meteo_station.infrastructure.amqp.event.MeteoDataRegistrationEvent;

import javax.inject.Named;

@Named
public class MeteoDataPublisher {

    public void publish(MeteoDataRegistrationEvent meteoDataRegistrationEvent) {

    }
}
