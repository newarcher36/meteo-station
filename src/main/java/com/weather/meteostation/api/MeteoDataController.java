package com.weather.meteostation.api;


import com.weather.meteostation.api.dto.MeteoDataDto;
import com.weather.meteostation.api.dto.MeteoDataStatisticsDto;
import com.weather.meteostation.application.usecase.GetMeteoDataStatistics;
import com.weather.meteostation.application.usecase.SaveMeteoData;
import com.weather.meteostation.domain.MeteoData;
import com.weather.meteostation.domain.MeteoDataStatistics;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/meteo-data")
public class MeteoDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeteoDataController.class);
    private final GetMeteoDataStatistics getMeteoDataStatistics;
    private final SaveMeteoData saveMeteoData;

    public MeteoDataController(SaveMeteoData saveMeteoData, GetMeteoDataStatistics getMeteoDataStatistics) {
        this.saveMeteoData = saveMeteoData;
        this.getMeteoDataStatistics = getMeteoDataStatistics;
    }

    @GetMapping
    public MeteoDataStatisticsDto getMeteoDataStatistics(@RequestParam(name = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate, @RequestParam(name = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        MeteoDataStatistics meteoDataStatistics = getMeteoDataStatistics.get(fromDate, toDate);
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
        saveMeteoData.register(map(meteoDataDto));
    }

    private MeteoData map(MeteoDataDto meteoDataDto) {
        return MeteoData.builder()
                .withRegistrationDate(meteoDataDto.getTimestamp())
                .withTemperature(meteoDataDto.getTemperature())
                .withPressure(meteoDataDto.getPressure())
                .withElevation(meteoDataDto.getElevation())
                .build();
    }
}
