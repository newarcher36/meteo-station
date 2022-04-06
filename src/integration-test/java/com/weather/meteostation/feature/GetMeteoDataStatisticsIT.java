package com.weather.meteostation.feature;

import com.weather.meteostation.infrastructure.amqp.event.GetTemperatureStatisticsEvent;
import com.weather.meteostation.infrastructure.amqp.event.TemperatureStatisticsEvent;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.joda.time.LocalDate.parse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@ContextConfiguration(initializers = {GetMeteoDataStatisticsIT.Initializer.class})
@Sql(scripts = {"/schema.sql","/insert-temperature-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class GetMeteoDataStatisticsIT {

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
    getMeteoDataStatistics() {

        TemperatureStatisticsEvent temperatureStatisticsEvent = TemperatureStatisticsEvent.builder()
                .withCurrentTemperature(25f)
                .withAvgTemperature(20f)
                .withMaxTemperature(30f)
                .withMinTemperature(10f)
                .build();

        BDDMockito.given(rabbitTemplate.convertSendAndReceiveAsType(anyString(), anyString(), any(GetTemperatureStatisticsEvent.class), any())).willReturn(temperatureStatisticsEvent);

        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectBody("currentTemperature", is(25f))
                .expectBody("avgTemperature", is(20f))
                .expectBody("maxTemperature", is(30f))
                .expectBody("minTemperature", is(10f))
                .build();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/meteo-station/api/v1/meteo-data?fromDate=2022-01-01&toDate=2022-03-01")
                .then()
                .spec(responseSpec);
    }
}
