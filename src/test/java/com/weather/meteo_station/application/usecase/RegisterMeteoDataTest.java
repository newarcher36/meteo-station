package com.weather.meteo_station.application.usecase;

import com.weather.meteo_station.domain.MeteoData;
import com.weather.meteo_station.infrastructure.amqp.client.MeteoDataPublisher;
import com.weather.meteo_station.infrastructure.amqp.event.MeteoDataRegistrationEvent;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class RegisterMeteoDataTest {

    @Mock
    private MeteoDataPublisher meteoDataPublisher;

    @Captor
    private ArgumentCaptor<MeteoDataRegistrationEvent> meteoDataRegistrationEventCaptor;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test void
    registerTemperature() {
        MeteoData actualMeteoData = MeteoData.builder()
                .withTimestamp(LocalDateTime.parse("2022-01-01"))
                .withTemperature(23f)
                .withPressure(950f)
                .withElevation(526f)
                .build();

        RegisterMeteoData registerMeteoData = new RegisterMeteoData(meteoDataPublisher);
        registerMeteoData.register(actualMeteoData);

        verify(meteoDataPublisher).publish(meteoDataRegistrationEventCaptor.capture());

        MeteoDataRegistrationEvent expectedMeteoDataRegistrationEvent = meteoDataRegistrationEventCaptor.getValue();

        assertThat(actualMeteoData)
                .usingRecursiveComparison()
                .isEqualTo(expectedMeteoDataRegistrationEvent);
    }
}