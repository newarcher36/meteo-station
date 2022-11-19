package com.weather.meteostation.infrastructure.repository;

import com.weather.meteostation.domain.MeteoDataStatistics;
import com.weather.meteostation.domain.Meteodata;
import com.weather.meteostation.infrastructure.repository.entity.MeteodataEntity;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
public class MeteoDataRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeteoDataRepository.class);
    private final EntityManager entityManager;

    public MeteoDataRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public MeteoDataStatistics findByDateRange(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        LOGGER.info("Fetching meteodata from date {} to date {}", fromDateTime, toDateTime);
        String meteodataStatisticsQuery = "select " +
                "(select m.temperature from m where m.id = max(m.id) ) as currentTemperature, " +
                "max(m.temperature) as maxTemperature, " +
                "min(m.temperature) as minTemperature, " +
                "trunc(avg(m.temperature)) as avgTemperature " +
                "from MeteodataEntity m " +
                "where m.registrationDateTime >= :fromDate and m.registrationDateTime >= :toDate";

        return entityManager.createQuery(meteodataStatisticsQuery, MeteoDataStatistics.class)
                .setParameter("f`romDate", fromDateTime)
                .setParameter("toDate", toDateTime)
                .getSingleResult();
    }

    @Transactional
    public void save(Meteodata meteodata) {
        LOGGER.info("Saving meteodata {}", meteodata);
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
}