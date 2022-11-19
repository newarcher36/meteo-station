package com.weather.meteostation.domain.exception;

public class MeteodataNotFoundException extends RuntimeException {
    public MeteodataNotFoundException(String message) {
        super(message);
    }
}
