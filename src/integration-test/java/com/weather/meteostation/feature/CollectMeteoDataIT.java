package com.weather.meteostation.feature;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.meteostation.infrastructure.amqp.event.SaveTemperatureDataEvent;
import com.weather.meteostation.infrastructure.amqp.event.TemperatureDataEventSaved;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@ContextConfiguration(initializers = {CollectMeteoDataIT.Initializer.class})
@Sql(scripts = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CollectMeteoDataIT {

    @Value("${local.server.port}")
    private int port;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    @Container
    public final static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>("postgres:12")
            .withUsername(USERNAME)
            .withPassword(PASSWORD);

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.username=" + postgresSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgresSQLContainer.getPassword(),
                    "spring.datasource.url=" + postgresSQLContainer.getJdbcUrl()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test void
    collectMeteoDataFromSensor() throws IOException {

        TemperatureDataEventSaved temperatureDataEventSaved = TemperatureDataEventSaved.builder()
                .withMeteoDataId(1L)
                .withSuccess(true)
                .build();

        BDDMockito.given(rabbitTemplate.convertSendAndReceiveAsType(anyString(), anyString(), any(SaveTemperatureDataEvent.class), any())).willReturn(temperatureDataEventSaved);

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
