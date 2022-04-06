package com.weather.meteostation.infrastructure.amqp.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder(setterPrefix = "with")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TemperatureDataEventSaved {
    private Long meteoDataId;
    private boolean success;
}
