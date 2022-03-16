package com.weather.meteo_station.feature;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.meteo_station.infrastructure.amqp.event.MeteoDataRegistrationEvent;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.doNothing;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MeteoDataIT {

    @Value("${local.server.port}")
    private int port;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void
    collectMeteoDataFromSensor() throws IOException {

        doNothing().when(rabbitTemplate).convertAndSend(any(MeteoDataRegistrationEvent.class));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(aMeteoDataDto())
                .post("/meteo-station/api/v1/meteo-data")
                .then()
                .statusCode(HTTP_OK);
    }

    private JsonNode aMeteoDataDto() throws IOException {
        return new ObjectMapper().readTree("{\"timestamp\":1642361588112, \"temperature\":3, \"pressure\":980, \"elevation\":526}");
    }
}
