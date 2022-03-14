package com.weather.meteo_station.infrastructure.amqp.event;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.LocalDateTime;

@Builder(setterPrefix = "with")
@Getter
@EqualsAndHashCode
@ToString
public class MeteoDataRegistrationEvent {

    private final LocalDateTime timestamp;
    private final Float temperature;
    private final Float pressure;
    private final Float elevation;
}
