package com.weather.meteostation.api;


import com.weather.meteostation.api.dto.MeteoDataDto;
import com.weather.meteostation.api.dto.MeteoDataStatisticsDto;
import com.weather.meteostation.application.usecase.GetMeteoDataStatistics;
import com.weather.meteostation.application.usecase.SaveMeteoData;
import com.weather.meteostation.domain.MeteoDataStatistics;
import com.weather.meteostation.domain.Meteodata;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meteodata")
public class MeteoDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeteoDataController.class);
    private final GetMeteoDataStatistics getMeteoDataStatistics;
    private final SaveMeteoData saveMeteoData;

    public MeteoDataController(SaveMeteoData saveMeteoData, GetMeteoDataStatistics getMeteoDataStatistics) {
        this.saveMeteoData = saveMeteoData;
        this.getMeteoDataStatistics = getMeteoDataStatistics;
    }

    @GetMapping("/today")
    public MeteoDataStatisticsDto getMeteoDataStatistics() {
        MeteoDataStatistics meteoDataStatistics = getMeteoDataStatistics.get(todayStartOfDay(), todayEndOfDay());
        return MeteoDataStatisticsDto.builder()
                .withCurrentTemperature(meteoDataStatistics.getCurrentTemperature())
                .withAvgTemperature(meteoDataStatistics.getAvgTemperature())
                .withMaxTemperature(meteoDataStatistics.getMaxTemperature())
                .withMinTemperature(meteoDataStatistics.getMinTemperature())
                .build();
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
        saveMeteoData.save(map(meteoDataDto));
    }

    private Meteodata map(MeteoDataDto meteoDataDto) {
        return Meteodata.builder()
                .withRegistrationDateTime(meteoDataDto.getTimestamp())
                .withTemperature(meteoDataDto.getTemperature())
                .withPressure(meteoDataDto.getPressure())
                .withElevation(meteoDataDto.getElevation())
                .build();
    }

    private LocalDateTime todayStartOfDay() {
        return LocalDateTime.now()
                .withHourOfDay(0)
                .withMinuteOfHour(0)
                .withSecondOfMinute(0);
    }

    private LocalDateTime todayEndOfDay() {
        return LocalDateTime.now()
                .withHourOfDay(23)
                .withMinuteOfHour(59)
                .withSecondOfMinute(59);
    }
}
