package com.weather.meteostation.application.usecase;

import com.weather.meteostation.domain.MeteoDataStatistics;
import com.weather.meteostation.infrastructure.amqp.client.GetTemperatureStatisticsPublisher;
import com.weather.meteostation.infrastructure.amqp.event.GetTemperatureStatisticsEvent;
import com.weather.meteostation.infrastructure.repository.MeteoDataRepository;
import org.joda.time.LocalDate;

import javax.inject.Named;
import java.util.List;

import static java.util.Optional.ofNullable;

@Named
public class GetMeteoDataStatistics {

    private final MeteoDataRepository meteoDataRepository;
    private final GetTemperatureStatisticsPublisher getTemperatureStatisticsPublisher;

    public GetMeteoDataStatistics(MeteoDataRepository meteoDataRepository, GetTemperatureStatisticsPublisher getTemperatureStatisticsPublisher) {
        this.meteoDataRepository = meteoDataRepository;
        this.getTemperatureStatisticsPublisher = getTemperatureStatisticsPublisher;
    }

    public MeteoDataStatistics get(LocalDate fromDate, LocalDate toDate) {
        List<Long> meteoDataIds = meteoDataRepository.findByDateRange(fromDate, toDate);
        GetTemperatureStatisticsEvent getTemperatureStatisticsEvent = GetTemperatureStatisticsEvent.builder()
                .withMeteoDataIds(meteoDataIds.toArray(Long[]::new))
                .build();
        MeteoDataStatistics meteoDataStatistics = getTemperatureStatisticsPublisher.publish(getTemperatureStatisticsEvent);
        return ofNullable(meteoDataStatistics).orElseThrow(() -> new IllegalArgumentException("Could not get temperature statistics"));
    }
}
