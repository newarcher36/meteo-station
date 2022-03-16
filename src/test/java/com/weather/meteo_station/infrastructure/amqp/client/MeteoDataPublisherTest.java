package com.weather.meteo_station.infrastructure.amqp.client;

import com.weather.meteo_station.infrastructure.amqp.event.MeteoDataRegistrationEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.joda.time.LocalDateTime.now;
import static org.mockito.BDDMockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class MeteoDataPublisherTest {

    @Captor
    private ArgumentCaptor<MeteoDataRegistrationEvent> meteoDataRegistrationEventCaptor;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test void
    publishTemperatureMeasure() {

        MeteoDataRegistrationEvent meteoDataRegistrationEvent = MeteoDataRegistrationEvent.builder()
                .withTimestamp(now())
                .withTemperature(23f)
                .withPressure(950f)
                .withElevation(526f)
                .build();

        MeteoDataPublisher meteoDataPublisher = new MeteoDataPublisher(rabbitTemplate);
        meteoDataPublisher.publish(meteoDataRegistrationEvent);

        verify(rabbitTemplate).convertAndSend(meteoDataRegistrationEventCaptor.capture());

        MeteoDataRegistrationEvent actualMeteoDataRegistrationEvent = meteoDataRegistrationEventCaptor.getValue();

        assertThat(actualMeteoDataRegistrationEvent).isEqualTo(meteoDataRegistrationEvent);
    }
}