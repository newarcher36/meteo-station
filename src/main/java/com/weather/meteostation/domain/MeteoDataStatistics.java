package com.weather.meteostation.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static java.util.Objects.isNull;

@Builder(setterPrefix = "with")
@Getter
@EqualsAndHashCode
@ToString
public class MeteoDataStatistics {
    private final Float currentTemperature;
    private final Float avgTemperature;
    private final Float maxTemperature;
    private final Float minTemperature;

    private MeteoDataStatistics(Float currentTemperature, Float avgTemperature, Float maxTemperature, Float minTemperature) {
        this.currentTemperature = currentTemperature;
        this.avgTemperature = avgTemperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        validate();
    }

    private void validate() {
        if (isNull(currentTemperature)) {
            throw new IllegalArgumentException("Current temperature is required");
        }
        if (isNull(avgTemperature)) {
            throw new IllegalArgumentException("Average temperature is required");
        }
        if (isNull(maxTemperature)) {
            throw new IllegalArgumentException("Max temperature is required");
        }
        if (isNull(minTemperature)) {
            throw new IllegalArgumentException("Min temperature is required");
        }
    }
}
