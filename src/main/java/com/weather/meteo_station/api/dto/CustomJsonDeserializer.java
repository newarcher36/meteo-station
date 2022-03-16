package com.weather.meteo_station.api.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.joda.time.LocalDateTime;

import java.io.IOException;

public class CustomJsonDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode timestamp = jsonParser.getCodec().readTree(jsonParser);
        return timestamp.isNumber() ? new LocalDateTime(timestamp.asLong()) : null;
    }
}
