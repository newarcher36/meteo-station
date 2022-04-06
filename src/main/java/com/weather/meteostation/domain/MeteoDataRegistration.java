package com.weather.meteostation.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.LocalDate;

import static java.util.Objects.isNull;

@Builder(setterPrefix = "with")
@Getter
@EqualsAndHashCode
@ToString
public class MeteoDataRegistration {

    private final Long id;
    private final LocalDate registrationDate;
    private final Float elevation;

    private MeteoDataRegistration(Long id, LocalDate registrationDate, Float elevation) {
        this.id = id;
        this.registrationDate = registrationDate;
        this.elevation = elevation;
        validate();
    }

    private void validate() {
        if (isNull(this.registrationDate)) {
            throw new IllegalArgumentException("Registration date is required");
        }

        if (isNull(this.elevation)) {
            throw new IllegalArgumentException("Elevation is required");
        }
    }
}
