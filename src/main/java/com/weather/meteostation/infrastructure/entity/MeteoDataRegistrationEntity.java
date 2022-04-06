package com.weather.meteostation.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.joda.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Builder(setterPrefix = "with")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(schema = "meteodata", name = "meteodata_register")
public class MeteoDataRegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_date", nullable = false)
    @Convert(converter = LocalDateConverter.class)
    private LocalDate registrationDate;

    @Column(name = "elevation", nullable = false)
    private Float elevation;
}
