package com.weather.meteo_station.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class MeteoDataDto {

    @JsonSerialize(using = CustomJsonSerializer.class)
    @JsonDeserialize(using = CustomJsonDeserializer.class)
    private final LocalDateTime timestamp;
    private final Float temperature;
    private final Float pressure;
    private final Float elevation;

    private MeteoDataDto(LocalDateTime timestamp, Float temperature, Float pressure, Float elevation) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.pressure = pressure;
        this.elevation = elevation;
        validate();
    }

    // TODO Add validations
    private void validate() {
        if (isNull(timestamp)) {
            throw new IllegalArgumentException("Temperature measure timestamp is required");
        }
        if (isNull(temperature)) {
            throw new IllegalArgumentException("Temperature value is required");
        }
    }
}
