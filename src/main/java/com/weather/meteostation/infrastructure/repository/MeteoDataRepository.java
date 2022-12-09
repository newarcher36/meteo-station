package com.weather.meteostation.infrastructure.repository;

import com.weather.meteostation.domain.MeteoDataStatistics;
import com.weather.meteostation.domain.Meteodata;
import com.weather.meteostation.infrastructure.repository.entity.MeteodataEntity;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.Optional;

@Named
public class MeteoDataRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeteoDataRepository.class);
    private final EntityManager entityManager;

    public MeteoDataRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public MeteoDataStatistics findByDateRange(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        LOGGER.info("Fetching meteodata from date {} to date {}", fromDateTime, toDateTime);
        String meteodataStatisticsQuery = "with meteodata_cte as " +
                "(" +
                "select * " +
                "from meteostation.meteodata " +
                "where registration_date_time between to_timestamp(:fromDate, 'DD-MM-YYYY hh24:mi:ss') and to_timestamp(:toDate, 'DD-MM-YYYY hh24:mi:ss') " +
                ")" +
                "select " +
                "(select cte.temperature from meteodata_cte cte order by cte.registration_date_time desc limit 1) as latestTemperature, " +
                "max(met.temperature) as maxTemperature, " +
                "min(met.temperature) as minTemperature, " +
                "trunc(avg(met.temperature)) as avgTemperature " +
                "from meteodata_cte met";

        Object[] meteoDataStatistics = (Object[]) entityManager.createNativeQuery(meteodataStatisticsQuery)
                .setParameter("fromDate", DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss").print(fromDateTime))
                .setParameter("toDate", DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss").print(toDateTime))
                .getSingleResult();
        return map(meteoDataStatistics);
    }

    @Transactional
    public void save(Meteodata meteodata) {
        LOGGER.debug("Saving meteodata {}", meteodata);
        entityManager.merge(map(meteodata));
    }

    private MeteodataEntity map(Meteodata meteodata) {
        return MeteodataEntity.builder()
                .withRegistrationDateTime(meteodata.getRegistrationDateTime())
                .withTemperature(meteodata.getTemperature())
                .withPressure(meteodata.getPressure())
                .withElevation(meteodata.getElevation())
                .build();
    }

    private MeteoDataStatistics map(Object[] meteoDataStatistics) {
        return MeteoDataStatistics.builder()
                .withCurrentTemperature(mapToFloat(meteoDataStatistics[0]))
                .withMaxTemperature(mapToFloat(meteoDataStatistics[1]))
                .withMinTemperature(mapToFloat(meteoDataStatistics[2]))
                .withAvgTemperature(mapToFloat(meteoDataStatistics[3]))
                .build();
    }

    private Float mapToFloat(Object meteoDataStatistic) {
        return Optional.ofNullable(meteoDataStatistic)
                .map(Object::toString)
                .map(Float::parseFloat)
                .orElse(null);
    }
}