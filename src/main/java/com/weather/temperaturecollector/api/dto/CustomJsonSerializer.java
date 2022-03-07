package com.weather.temperaturecollector.api.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDateTime;

import java.io.IOException;

public class CustomJsonSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeString(String.valueOf(value.toDateTime().getMillis()));
    }
}