package com.weather.meteostation.application.usecase;

import com.weather.meteostation.domain.MeteoDataStatistics;
import com.weather.meteostation.domain.exception.MeteodataNotFoundException;
import com.weather.meteostation.infrastructure.repository.MeteoDataRepository;
import org.joda.time.LocalDateTime;

import javax.inject.Named;

import static java.util.Optional.ofNullable;

@Named
public class GetMeteoDataStatistics {

    private final MeteoDataRepository meteoDataRepository;

    public GetMeteoDataStatistics(MeteoDataRepository meteoDataRepository) {
        this.meteoDataRepository = meteoDataRepository;
    }

    public MeteoDataStatistics get(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        MeteoDataStatistics meteodataByDateRange = meteoDataRepository.findByDateRange(fromDateTime, toDateTime);
        // TODO check
        return ofNullable(meteodataByDateRange)
                .orElseThrow(() -> new MeteodataNotFoundException("Could not get temperature statistics"));
    }
}
