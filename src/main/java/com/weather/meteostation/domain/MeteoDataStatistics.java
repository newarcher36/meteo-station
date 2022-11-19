package com.weather.meteostation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder(setterPrefix = "with")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MeteoDataStatistics {
    private Float currentTemperature;
    private Float avgTemperature;
    private Float maxTemperature;
    private Float minTemperature;
    private Float currentPressure;
    private Float avgPressure;
    private Float maxPressure;
    private Float minPressure;
}