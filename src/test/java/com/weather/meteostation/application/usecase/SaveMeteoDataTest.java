package com.weather.meteostation.application.usecase;

import com.weather.meteostation.domain.MeteoData;
import com.weather.meteostation.domain.MeteoDataRegistration;
import com.weather.meteostation.infrastructure.amqp.client.SaveTemperatureEventPublisher;
import com.weather.meteostation.infrastructure.amqp.event.SaveTemperatureDataEvent;
import com.weather.meteostation.infrastructure.amqp.event.TemperatureDataEventSaved;
import com.weather.meteostation.infrastructure.repository.MeteoDataRepository;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class SaveMeteoDataTest {

    @Mock
    private MeteoDataRepository meteoDataRepository;

    @Mock
    private SaveTemperatureEventPublisher saveTemperatureEventPublisher;

    @Captor
    private ArgumentCaptor<MeteoDataRegistration> meteoDataRegistrationCaptor;

    @Captor
    private ArgumentCaptor<SaveTemperatureDataEvent> saveTemperatureDataEventCaptor;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test void
    registerTemperature() {

        MeteoData meteoData = MeteoData.builder()
                .withRegistrationDate(LocalDateTime.parse("2022-01-01"))
                .withTemperature(23f)
                .withPressure(950f)
                .withElevation(526f)
                .build();

        MeteoDataRegistration savedMeteoDataRegistration = MeteoDataRegistration.builder()
                .withId(1L)
                .withRegistrationDate(LocalDate.parse("2022-01-01"))
                .withElevation(526f)
                .build();

        TemperatureDataEventSaved temperatureDataEventSaved = TemperatureDataEventSaved.builder()
                .withMeteoDataId(1L)
                .withSuccess(true)
                .build();

        given(meteoDataRepository.save(any(MeteoDataRegistration.class))).willReturn(savedMeteoDataRegistration);
        given(saveTemperatureEventPublisher.publish(any(SaveTemperatureDataEvent.class))).willReturn(temperatureDataEventSaved);

        SaveMeteoData saveMeteoData = new SaveMeteoData(saveTemperatureEventPublisher, meteoDataRepository);
        saveMeteoData.register(meteoData);

        verify(meteoDataRepository).save(meteoDataRegistrationCaptor.capture());
        verify(saveTemperatureEventPublisher).publish(saveTemperatureDataEventCaptor.capture());
        verifyNoMoreInteractions(meteoDataRepository);

        assertThat(meteoDataRegistrationCaptor.getValue())
                .isNotNull()
                .extracting(MeteoDataRegistration::getRegistrationDate, MeteoDataRegistration::getElevation)
                .containsExactly(LocalDate.parse("2022-01-01"), 526f);

        assertThat(saveTemperatureDataEventCaptor.getValue())
                .isNotNull()
                .extracting(SaveTemperatureDataEvent::getMeteoDataId, SaveTemperatureDataEvent::getTemperatureValue)
                .containsExactly(1L, 23f);
    }
}