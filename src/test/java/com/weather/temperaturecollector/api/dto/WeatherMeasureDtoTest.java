package com.weather.temperaturecollector.api.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.joda.time.LocalDateTime.now;

class WeatherMeasureDtoTest {

    @Test void
    validateTemperatureTimestamp() {
//        Throwable throwable = catchThrowable(() ->  new TemperatureMeasureDto(null, 23, pressure, elevation));
//        assertThat(throwable)
//                .isNotNull()
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("Temperature measure timestamp is required");
    }

    @Test void
    validateTemperatureValue() {
//        Throwable throwable = catchThrowable(() ->  new TemperatureMeasureDto(now(), null, pressure, elevation));
//        assertThat(throwable)
//                .isNotNull()
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("Temperature value is required");
    }
}