package com.weather.meteostation.api;

import com.weather.meteostation.api.dto.MeteoDataDto;
import com.weather.meteostation.application.usecase.GetMeteoDataStatistics;
import com.weather.meteostation.application.usecase.SaveMeteoData;
import com.weather.meteostation.domain.MeteoData;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class MeteoDataControllerTest {

    @Mock
    private SaveMeteoData saveMeteoData;

    @Mock
    private GetMeteoDataStatistics getMeteoDataStatistics;

    @Captor
    private ArgumentCaptor<MeteoData> temperatureMeasureDtoCaptor;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test void
    registerTemperatureMeasure() {
        MeteoDataController meteoDataController = new MeteoDataController(saveMeteoData, getMeteoDataStatistics);

        MeteoDataDto actualMeteoDataDto = MeteoDataDto.builder()
                .withTimestamp(LocalDateTime.parse("2022-01-01"))
                .withTemperature(23f)
                .withPressure(950f)
                .withElevation(526f)
                .build();

        meteoDataController.registerMeteoData(actualMeteoDataDto);

        verify(saveMeteoData).register(temperatureMeasureDtoCaptor.capture());

        MeteoDataDto expectedMeteoDataDto = MeteoDataDto.builder()
                .withTimestamp(LocalDateTime.parse("2022-01-01"))
                .withTemperature(23f)
                .withPressure(950f)
                .withElevation(526f)
                .build();

        assertThat(actualMeteoDataDto).isEqualTo(expectedMeteoDataDto);
    }
}