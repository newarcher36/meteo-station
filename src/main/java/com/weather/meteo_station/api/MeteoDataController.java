package com.weather.meteo_station.api;


import com.weather.meteo_station.api.dto.MeteoDataDto;
import com.weather.meteo_station.application.usecase.RegisterMeteoData;
import com.weather.meteo_station.domain.MeteoData;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/meteo-data")
public class MeteoDataController {

    private final RegisterMeteoData registerMeteoData;
    private static final Logger LOGGER = LoggerFactory.getLogger(MeteoDataController.class);

    public MeteoDataController(RegisterMeteoData registerMeteoData) {
        this.registerMeteoData = registerMeteoData;
    }

    @PostMapping
    public void registerMeteoData(@RequestParam("timestamp") Long timestamp, @RequestParam("temperature") Float temperature, @RequestParam("pressure") Float pressure, @RequestParam("elevation") Float elevation) {
        MeteoDataDto meteoDataDto = MeteoDataDto.builder()
                // TODO millis should be provided by weather station
                .withTimestamp(LocalDateTime.now())
                .withTemperature(temperature)
                .withPressure(pressure)
                .withElevation(elevation)
                .build();

        LOGGER.info("Meteo data received {}", meteoDataDto);

        this.registerMeteoData(meteoDataDto);
    }

    // TODO fix post request in arduino
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerMeteoData(@RequestBody MeteoDataDto meteoDataDto) {
        registerMeteoData.register(map(meteoDataDto));
    }

    private MeteoData map(MeteoDataDto meteoDataDto) {
        return MeteoData.builder()
                .withTimestamp(meteoDataDto.getTimestamp())
                .withTemperature(meteoDataDto.getTemperature())
                .withPressure(meteoDataDto.getPressure())
                .withElevation(meteoDataDto.getElevation())
                .build();
    }
}
