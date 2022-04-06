package com.weather.meteostation.domain;

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

    private final LocalDateTime registrationDate;
    private final Float temperature;
    private final Float pressure;
    private final Float elevation;

    private MeteoData(LocalDateTime registrationDate, Float temperature, Float pressure, Float elevation) {
        this.registrationDate = registrationDate;
        this.temperature = temperature;
        this.pressure = pressure;
        this.elevation = elevation;
        validate();
    }

    private void validate() {
        if (isNull(registrationDate)) {
            throw new IllegalArgumentException("Registration date is required");
        }
        if (isNull(temperature)) {
            throw new IllegalArgumentException("Temperature is required");
        }
        if (isNull(pressure)) {
            throw new IllegalArgumentException("Pressure is required");
        }
        if (isNull(elevation)) {
            throw new IllegalArgumentException("Elevation is required");
        }
    }
}
