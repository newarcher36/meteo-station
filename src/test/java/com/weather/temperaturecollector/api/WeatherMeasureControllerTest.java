package com.weather.temperaturecollector.api;

import com.weather.temperaturecollector.api.dto.WeatherMeasureDto;
import com.weather.temperaturecollector.application.usecase.RegisterTemperatureMeasure;
import com.weather.temperaturecollector.domain.WeatherMeasure;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class WeatherMeasureControllerTest {

    @Mock
    private RegisterTemperatureMeasure registerTemperatureMeasure;

    @Captor
    private ArgumentCaptor<WeatherMeasure> temperatureMeasureDtoCaptor;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void
    registerTemperatureMeasure() {
        WeatherMeasureController weatherMeasureController = new WeatherMeasureController(registerTemperatureMeasure);

        WeatherMeasureDto weatherMeasureDto = WeatherMeasureDto.builder()
                .withTimestamp(LocalDateTime.parse("2022-01-01"))
                //.withValue(23)
                .build();

        weatherMeasureController.registerTemperatureMeasure(weatherMeasureDto);

        verify(registerTemperatureMeasure).registerTemperature(temperatureMeasureDtoCaptor.capture());

//        assertThat(temperatureMeasureDtoCaptor.getValue())
//                .extracting(WeatherMeasure::getTimestamp, WeatherMeasure::getValue)
//                .containsExactly(LocalDateTime.parse("2022-01-01"), 23);
    }
}