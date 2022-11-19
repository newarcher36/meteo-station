package com.weather.meteostation.infrastructure.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.joda.time.LocalDateTime;

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
@Table(schema = "meteostation", name = "meteodata")
public class MeteodataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_date_time", nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime registrationDateTime;

    @Column(name = "temperature", nullable = false)
    private Float temperature;

    @Column(name = "pressure", nullable = false)
    private Float pressure;

    @Column(name = "elevation", nullable = false)
    private Float elevation;
}
