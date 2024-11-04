package com.codecool.solarwatch.customexception;

public class InvalidCityException extends RuntimeException {
    public InvalidCityException() {
        super("Invalid city");
    }
}
