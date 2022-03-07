package com.weather.temperaturecollector.application.usecase;

import com.weather.temperaturecollector.domain.WeatherMeasure;
import com.weather.temperaturecollector.infrastructure.amqp.client.TemperatureMeasurePublisher;
import com.weather.temperaturecollector.infrastructure.amqp.event.WeatherMeasureEvent;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class RegisterWeatherMeasureTest {

    @Mock
    private TemperatureMeasurePublisher temperatureMeasurePublisher;

    @Captor
    private ArgumentCaptor<WeatherMeasureEvent> temperatureMeasureEventCaptor;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void registerTemperature() {
        WeatherMeasure weatherMeasure = WeatherMeasure.builder()
                .withTimestamp(LocalDateTime.parse("2022-01-01"))
                //.withValue(23)
                .build();

        RegisterTemperatureMeasure registerTemperatureMeasure = new RegisterTemperatureMeasure(temperatureMeasurePublisher);
        registerTemperatureMeasure.registerTemperature(weatherMeasure);

        verify(temperatureMeasurePublisher).publish(temperatureMeasureEventCaptor.capture());

//        assertThat(temperatureMeasureEventCaptor.getValue())
//                .extracting(WeatherMeasureEvent::getTimestamp, WeatherMeasureEvent::getValue)
//                .containsExactly(LocalDateTime.parse("2022-01-01"), 23);
    }
}