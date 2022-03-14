package com.weather.meteo_station.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.LocalDateTime;

import static java.util.Objects.isNull;

@Builder(setterPrefix = "with")
@Getter
@EqualsAndHashCode
@ToString
public class MeteoData {

    private final LocalDateTime timestamp;
    private final Float temperature;
    private final Float pressure;
    private final Float elevation;

    private MeteoData(LocalDateTime timestamp, Float temperature, Float pressure, Float elevation) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.pressure = pressure;
        this.elevation = elevation;
        validate();
    }

    private void validate() {
        if (isNull(timestamp)) {
            throw new IllegalArgumentException("Meteo data timestamp is required");
        }
        if (isNull(temperature)) {
            throw new IllegalArgumentException("Temperature value is required");
        }
        if (isNull(pressure)) {
            throw new IllegalArgumentException("Pressure value is required");
        }
        if (isNull(elevation)) {
            throw new IllegalArgumentException("Elevation value is required");
        }
    }
}
