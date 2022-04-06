package com.weather.meteostation.api.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomJsonSerializerTest {

    @Test
    void serializeTemperatureMeasureDateAndTimeAsEpochMilliseconds() throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonEvent = objectMapper.writeValueAsString(new TemperatureMeasureDto(LocalDateTime.parse("2022-01-01"), 23, pressure, elevation));
//        assertThat(jsonEvent).isEqualTo("{\"timestamp\":\"1640991600000\",\"value\":23}");
    }
}