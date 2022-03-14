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
    private ArgumentCaptor<MeteoDataRegistrationEvent> temperatureMeasureEventCaptor;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void registerTemperature() {
        MeteoData meteoData = MeteoData.builder()
                .withTimestamp(LocalDateTime.parse("2022-01-01"))
                //.withValue(23)
                .build();

        RegisterMeteoData registerMeteoData = new RegisterMeteoData(meteoDataPublisher);
        registerMeteoData.register(meteoData);

        verify(meteoDataPublisher).publish(temperatureMeasureEventCaptor.capture());

//        assertThat(temperatureMeasureEventCaptor.getValue())
//                .extracting(WeatherMeasureEvent::getTimestamp, WeatherMeasureEvent::getValue)
//                .containsExactly(LocalDateTime.parse("2022-01-01"), 23);
    }
}