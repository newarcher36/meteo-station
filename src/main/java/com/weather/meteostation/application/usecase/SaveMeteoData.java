package com.weather.meteostation.application.usecase;

import com.weather.meteostation.domain.MeteoData;
import com.weather.meteostation.domain.MeteoDataRegistration;
import com.weather.meteostation.infrastructure.amqp.client.SaveTemperatureEventPublisher;
import com.weather.meteostation.infrastructure.amqp.event.SaveTemperatureDataEvent;
import com.weather.meteostation.infrastructure.amqp.event.TemperatureDataSavedEvent;
import com.weather.meteostation.infrastructure.repository.MeteoDataRepository;

import javax.inject.Named;

import static java.util.Objects.isNull;

@Named
public class SaveMeteoData {

    private final SaveTemperatureEventPublisher saveTemperatureEventPublisher;
    private final MeteoDataRepository meteoDataRepository;

    public SaveMeteoData(SaveTemperatureEventPublisher saveTemperatureEventPublisher, MeteoDataRepository meteoDataRepository) {
        this.saveTemperatureEventPublisher = saveTemperatureEventPublisher;
        this.meteoDataRepository = meteoDataRepository;
    }

    public void save(MeteoData meteoData) {
        MeteoDataRegistration meteoDataRegistration = MeteoDataRegistration.builder()
                .withRegistrationDate(meteoData.getRegistrationDate().toLocalDate())
                .withElevation(meteoData.getElevation())
                .build();
        MeteoDataRegistration savedMeteoDataRegistration = meteoDataRepository.save(meteoDataRegistration);

        SaveTemperatureDataEvent saveTemperatureDataEvent = SaveTemperatureDataEvent.builder()
                .withMeteoDataId(savedMeteoDataRegistration.getId())
                .withTemperatureValue(meteoData.getTemperature())
                .build();

        TemperatureDataSavedEvent temperatureDataSavedEvent = saveTemperatureEventPublisher.publish(saveTemperatureDataEvent);

        if (isNull(temperatureDataSavedEvent)) {
            meteoDataRepository.deleteById(savedMeteoDataRegistration.getId());
            // TODO Should not be an IllegalArgumentException but a domain exception
            throw new IllegalArgumentException(String.format("Could not save meteo data with id [%s]", savedMeteoDataRegistration.getId()));
        }
    }
}
