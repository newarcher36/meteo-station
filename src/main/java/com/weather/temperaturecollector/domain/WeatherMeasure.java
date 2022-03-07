package com.weather.temperaturecollector.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.LocalDateTime;

@Builder(setterPrefix = "with")
@Getter
@EqualsAndHashCode
@ToString
public class WeatherMeasure {

    private final LocalDateTime timestamp;
    private final Float temperature;
    private final Float pressure;
    private final Float elevation;
}
