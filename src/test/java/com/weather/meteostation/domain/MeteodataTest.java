package com.weather.meteostation.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.joda.time.LocalDateTime.now;

public class MeteodataTest {

    @Test void
    validateMeteoDataTimestamp() {
        Throwable throwable = catchThrowable(() ->  Meteodata.builder()
                .withRegistrationDateTime(null)
                .build());

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Registration date is required");
    }

    @Test void
    validateTemperatureValue() {
        Throwable throwable = catchThrowable(() ->  Meteodata.builder()
                .withRegistrationDateTime(now())
                .withTemperature(null)
                .build());

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Temperature is required");
    }

    @Test void
    validatePressureValue() {
        Throwable throwable = catchThrowable(() ->  Meteodata.builder()
                .withRegistrationDateTime(now())
                .withTemperature(23f)
                .withPressure(null)
                .build());

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Pressure is required");
    }

    @Test void
    validateElevationValue() {
        Throwable throwable = catchThrowable(() ->  Meteodata.builder()
                .withRegistrationDateTime(now())
                .withTemperature(23f)
                .withPressure(950f)
                .withElevation(null)
                .build());

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Elevation is required");
    }
}
