package com.weather.meteostation.infrastructure.amqp.client;

import com.weather.meteostation.infrastructure.amqp.event.SaveTemperatureDataEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class SaveTemperatureEventPublisherTest {

    @Captor
    private ArgumentCaptor<SaveTemperatureDataEvent> meteoDataRegistrationEventCaptor;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test void
    publishTemperatureMeasure() {

        SaveTemperatureDataEvent saveTemperatureDataEvent = SaveTemperatureDataEvent.builder()
                .withMeteoDataId(1L)
                .withTemperatureValue(23f)
                .build();

        SaveTemperatureEventPublisher saveTemperatureEventPublisher = new SaveTemperatureEventPublisher(rabbitTemplate);
        saveTemperatureEventPublisher.publish(saveTemperatureDataEvent);

        verify(rabbitTemplate).convertAndSend(meteoDataRegistrationEventCaptor.capture());

        assertThat(meteoDataRegistrationEventCaptor.getValue())
                .isNotNull()
                .extracting(SaveTemperatureDataEvent::getMeteoDataId, SaveTemperatureDataEvent::getTemperatureValue)
                .containsExactly(1L, 23f);
    }
}