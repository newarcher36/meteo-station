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
public class Meteodata {

    private final LocalDateTime registrationDateTime;
    private final Float temperature;
    private final Float pressure;
    private final Float elevation;

    private Meteodata(LocalDateTime registrationDateTime, Float temperature, Float pressure, Float elevation) {
        this.registrationDateTime = registrationDateTime;
        this.temperature = temperature;
        this.pressure = pressure;
        this.elevation = elevation;
        validate();
    }

    private void validate() {
        if (isNull(registrationDateTime)) {
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