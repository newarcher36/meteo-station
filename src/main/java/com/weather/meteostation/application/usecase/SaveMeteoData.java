package com.weather.meteostation.application.usecase;

import com.weather.meteostation.domain.MeteoData;
import com.weather.meteostation.domain.MeteoDataRegistration;
import com.weather.meteostation.infrastructure.amqp.client.SaveTemperatureEventPublisher;
import com.weather.meteostation.infrastructure.amqp.event.SaveTemperatureDataEvent;
import com.weather.meteostation.infrastructure.amqp.event.TemperatureDataEventSaved;
import com.weather.meteostation.infrastructure.repository.MeteoDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.Objects;

import static java.util.Objects.*;

@Named
public class SaveMeteoData {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveMeteoData.class);
    private final SaveTemperatureEventPublisher saveTemperatureEventPublisher;
    private final MeteoDataRepository meteoDataRepository;

    public SaveMeteoData(SaveTemperatureEventPublisher saveTemperatureEventPublisher, MeteoDataRepository meteoDataRepository) {
        this.saveTemperatureEventPublisher = saveTemperatureEventPublisher;
        this.meteoDataRepository = meteoDataRepository;
    }

    public void register(MeteoData meteoData) {
        MeteoDataRegistration meteoDataRegistration = MeteoDataRegistration.builder()
                .withRegistrationDate(meteoData.getRegistrationDate().toLocalDate())
                .withElevation(meteoData.getElevation())
                .build();
        MeteoDataRegistration savedMeteoDataRegistration = meteoDataRepository.save(meteoDataRegistration);

        SaveTemperatureDataEvent saveTemperatureDataEvent = SaveTemperatureDataEvent.builder()
                .withMeteoDataId(savedMeteoDataRegistration.getId())
                .withTemperatureValue(meteoData.getTemperature())
                .build();

        TemperatureDataEventSaved temperatureDataEventSaved = saveTemperatureEventPublisher.publish(saveTemperatureDataEvent);
        LOGGER.info("Response is : {}", temperatureDataEventSaved);

        if (isNull(temperatureDataEventSaved) || !temperatureDataEventSaved.isSuccess()) {
            meteoDataRepository.deleteById(savedMeteoDataRegistration.getId());
            // TODO Should not be an IllegalArgumentException but a domain exception
            throw new IllegalArgumentException(String.format("Could not save meteo data with id [{%s}]", savedMeteoDataRegistration.getId()));
        }
    }
}
