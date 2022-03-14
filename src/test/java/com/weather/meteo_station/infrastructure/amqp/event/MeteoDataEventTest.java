package com.weather.meteo_station.infrastructure.amqp.event;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.joda.time.LocalDateTime.now;

public class MeteoDataEventTest {

    @Test void
    validateMeteoDataTimestamp() {
        Throwable throwable = catchThrowable(() ->  MeteoDataRegistrationEvent.builder()
                .withTimestamp(null)
                .build());

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Meteo data timestamp is required");
    }

    @Test void
    validateTemperatureValue() {
        Throwable throwable = catchThrowable(() ->  MeteoDataRegistrationEvent.builder()
                .withTimestamp(now())
                .withTemperature(null)
                .build());

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Temperature value is required");
    }

    @Test void
    validatePressureValue() {
        Throwable throwable = catchThrowable(() ->  MeteoDataRegistrationEvent.builder()
                .withTimestamp(now())
                .withTemperature(23f)
                .withPressure(null)
                .build());

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Pressure value is required");
    }

    @Test void
    validateElevationValue() {
        Throwable throwable = catchThrowable(() ->  MeteoDataRegistrationEvent.builder()
                .withTimestamp(now())
                .withTemperature(23f)
                .withPressure(950f)
                .withElevation(null)
                .build());

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Elevation value is required");
    }
}
