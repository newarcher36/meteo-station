package com.weather.meteostation.application.usecase;

import com.weather.meteostation.domain.Meteodata;
import com.weather.meteostation.infrastructure.repository.MeteoDataRepository;

import javax.inject.Named;

@Named
public class SaveMeteoData {

    private final MeteoDataRepository meteoDataRepository;

    public SaveMeteoData(MeteoDataRepository meteoDataRepository) {
        this.meteoDataRepository = meteoDataRepository;
    }

    public void save(Meteodata meteoData) {
        meteoDataRepository.save(meteoData);
    }
}
