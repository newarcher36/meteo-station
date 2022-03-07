package com.weather.temperaturecollector.feature;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TemperatureCollectorIT {

    @Value("${local.server.port}")
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void
    collectTemperatureFromSensor() throws IOException {
        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(aTemperatureMeasure())
                .post("/weather/api/v1/temperature")
                .then()
                .statusCode(HTTP_OK);
    }

    private JsonNode aTemperatureMeasure() throws IOException {
        return new ObjectMapper().readTree("{\"timestamp\":1642361588112, \"value\":23}");
    }
}
