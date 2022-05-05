package com.weather.meteostation.application.usecase;

import com.weather.meteostation.domain.MeteoData;
import com.weather.meteostation.domain.MeteoDataRegistration;
import com.weather.meteostation.infrastructure.amqp.client.SaveTemperatureEventPublisher;
import com.weather.meteostation.infrastructure.amqp.event.SaveTemperatureDataEvent;
import com.weather.meteostation.infrastructure.amqp.event.TemperatureDataSavedEvent;
import com.weather.meteostation.infrastructure.repository.MeteoDataRepository;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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

    private SaveMeteoData saveMeteoData;

    @BeforeEach
    void setUp() {
        openMocks(this);
        saveMeteoData = new SaveMeteoData(saveTemperatureEventPublisher, meteoDataRepository);
    }

    @Test void
    saveMeteoData() {

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

        TemperatureDataSavedEvent temperatureDataSavedEvent = TemperatureDataSavedEvent.builder()
                .withId(1L)
                .withMeteoDataId(1L)
                .withTemperatureValue(23f)
                .build();

        given(meteoDataRepository.save(any(MeteoDataRegistration.class))).willReturn(savedMeteoDataRegistration);
        given(saveTemperatureEventPublisher.publish(any(SaveTemperatureDataEvent.class))).willReturn(temperatureDataSavedEvent);

        saveMeteoData.save(meteoData);

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

    @Test void
    failSaveMeteoDataWhenTemperatureIsNotSaved() {

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

        given(meteoDataRepository.save(any(MeteoDataRegistration.class))).willReturn(savedMeteoDataRegistration);
        given(saveTemperatureEventPublisher.publish(any(SaveTemperatureDataEvent.class))).willReturn(null);

        Throwable throwable = catchThrowable(() -> saveMeteoData.save(meteoData));

        verify(meteoDataRepository).deleteById(1L);
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Could not save meteo data with id [1]");
    }
}