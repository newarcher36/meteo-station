package com.weather.meteostation.infrastructure.repository.entity;

import org.joda.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.util.Optional;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime)
                .map(l -> l.toDateTime().getMillis())
                .map(Timestamp::new)
                .orElse(null);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        return Optional.ofNullable(timestamp)
                .map(Timestamp::getTime)
                .map(LocalDateTime::new)
                .orElse(null);
    }
}