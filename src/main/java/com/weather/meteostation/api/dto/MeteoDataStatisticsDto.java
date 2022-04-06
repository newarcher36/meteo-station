package com.weather.meteostation.api.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(setterPrefix = "with")
@Getter
@EqualsAndHashCode
@ToString
public class MeteoDataStatisticsDto {
    private final Float currentTemperature;
    private final Float avgTemperature;
    private final Float maxTemperature;
    private final Float minTemperature;
}
