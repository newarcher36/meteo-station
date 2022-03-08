package com.weather.meteo_station.api;


import com.weather.meteo_station.api.dto.WeatherMeasureDto;
import com.weather.meteo_station.application.usecase.RegisterTemperatureMeasure;
import com.weather.meteo_station.domain.WeatherMeasure;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class WeatherMeasureController {

    private final RegisterTemperatureMeasure registerTemperatureMeasure;
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherMeasureController.class);

    public WeatherMeasureController(RegisterTemperatureMeasure registerTemperatureMeasure) {
        this.registerTemperatureMeasure = registerTemperatureMeasure;
    }

    @PostMapping
    public void registerTemperatureMeasure(@RequestParam("timestamp") Long timestamp, @RequestParam("temperature") Float temperature, @RequestParam("pressure") Float pressure, @RequestParam("elevation") Float elevation) {
        WeatherMeasureDto weatherMeasureDto = WeatherMeasureDto.builder()
                // TODO millis should be provided by weather station
                .withTimestamp(LocalDateTime.now())
                .withTemperature(temperature)
                .withPressure(pressure)
                .withElevation(elevation)
                .build();

        LOGGER.info("Weather measure received {}", weatherMeasureDto);

        this.registerTemperatureMeasure(weatherMeasureDto);
    }

    // TODO fix post request in arduino
    //@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerTemperatureMeasure(WeatherMeasureDto weatherMeasureDto) {
        registerTemperatureMeasure.registerTemperature(map(weatherMeasureDto));
    }

    private WeatherMeasure map(WeatherMeasureDto weatherMeasureDto) {
        return WeatherMeasure.builder()
                .withTimestamp(weatherMeasureDto.getTimestamp())
                .withTemperature(weatherMeasureDto.getTemperature())
                .withPressure(weatherMeasureDto.getPressure())
                .withElevation(weatherMeasureDto.getElevation())
                .build();
    }
}
