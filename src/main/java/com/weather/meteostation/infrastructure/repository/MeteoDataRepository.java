package com.weather.meteostation.infrastructure.repository;

import com.weather.meteostation.domain.MeteoDataRegistration;
import com.weather.meteostation.infrastructure.entity.MeteoDataRegistrationEntity;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;

@Named
public class MeteoDataRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeteoDataRepository.class);
    private final EntityManager entityManager;

    public MeteoDataRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Long> findByDateRange(LocalDate fromDate, LocalDate toDate) {
        LOGGER.info("Retrieving meteo data from date {} to date {}", fromDate, toDate);
        String query = "select m.id from MeteoDataRegistrationEntity m where m.registrationDate >= :fromDate and m.registrationDate >= :toDate";
        return entityManager.createQuery(query, Long.class)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .getResultList();
    }

    @Transactional
    public MeteoDataRegistration save(MeteoDataRegistration meteoDataRegistration) {
        LOGGER.info("Saving new meteo data {}", meteoDataRegistration);
        MeteoDataRegistrationEntity meteoDataRegistrationEntity = entityManager.merge(map(meteoDataRegistration));
        return map(meteoDataRegistrationEntity);
    }

    @Transactional
    public void deleteById(Long meteoDataId) {
        LOGGER.info("Deleting meteo data with id {}", meteoDataId);
        entityManager.detach(MeteoDataRegistration.builder().withId(meteoDataId).build());
    }

    private MeteoDataRegistrationEntity map(MeteoDataRegistration meteoDataRegistration) {
        return MeteoDataRegistrationEntity.builder()
                .withRegistrationDate(meteoDataRegistration.getRegistrationDate())
                .withElevation(meteoDataRegistration.getElevation())
                .build();
    }

    private MeteoDataRegistration map(MeteoDataRegistrationEntity meteoDataRegistrationEntity) {
        return MeteoDataRegistration.builder()
                .withId(meteoDataRegistrationEntity.getId())
                .withRegistrationDate(meteoDataRegistrationEntity.getRegistrationDate())
                .withElevation(meteoDataRegistrationEntity.getElevation())
                .build();
    }
}
