package com.weather.meteostation.application.usecase;

import com.weather.meteostation.domain.Meteodata;
import com.weather.meteostation.infrastructure.repository.MeteoDataRepository;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class SaveMeteodataTest {

    @Mock
    private MeteoDataRepository meteoDataRepository;

    @Captor
    private ArgumentCaptor<Meteodata> meteoDataCaptor;

    private SaveMeteoData saveMeteoData;

    @BeforeEach
    void setUp() {
        openMocks(this);
        saveMeteoData = new SaveMeteoData(meteoDataRepository);
    }

    @Test void
    saveMeteoData() {
        doNothing().when(meteoDataRepository).save(any(Meteodata.class));

        Meteodata meteoData = Meteodata.builder()
                .withRegistrationDateTime(LocalDateTime.parse("2022-01-01"))
                .withTemperature(23f)
                .withPressure(950f)
                .withElevation(526f)
                .build();
        saveMeteoData.save(meteoData);

        verify(meteoDataRepository).save(meteoDataCaptor.capture());
        assertThat(meteoDataCaptor.getValue())
                .isNotNull()
                .hasFieldOrPropertyWithValue("registrationDateTime", LocalDateTime.parse("2022-01-01T00:00:00.000"))
                .hasFieldOrPropertyWithValue("temperature",23f)
                .hasFieldOrPropertyWithValue("pressure",950f)
                .hasFieldOrPropertyWithValue("elevation",526f);
    }
}