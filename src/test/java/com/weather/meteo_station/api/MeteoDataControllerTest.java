package com.weather.meteo_station.api;

import com.weather.meteo_station.api.dto.MeteoDataDto;
import com.weather.meteo_station.application.usecase.RegisterMeteoData;
import com.weather.meteo_station.domain.MeteoData;
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
    private RegisterMeteoData registerMeteoData;

    @Captor
    private ArgumentCaptor<MeteoData> temperatureMeasureDtoCaptor;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void
    registerTemperatureMeasure() {
        MeteoDataController meteoDataController = new MeteoDataController(registerMeteoData);

        MeteoDataDto meteoDataDto = MeteoDataDto.builder()
                .withTimestamp(LocalDateTime.parse("2022-01-01"))
                //.withValue(23)
                .build();

        //weatherMeasureController.registerTemperatureMeasure(weatherMeasureDto);

        verify(registerMeteoData).register(temperatureMeasureDtoCaptor.capture());

//        assertThat(temperatureMeasureDtoCaptor.getValue())
//                .extracting(WeatherMeasure::getTimestamp, WeatherMeasure::getValue)
//                .containsExactly(LocalDateTime.parse("2022-01-01"), 23);
    }
}