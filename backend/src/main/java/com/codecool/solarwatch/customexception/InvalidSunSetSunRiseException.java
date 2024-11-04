package com.codecool.solarwatch.customexception;

public class InvalidSunSetSunRiseException extends RuntimeException {
    public InvalidSunSetSunRiseException() {
        super("No sunset/sunrise data has been found at the given time and city");
    }
}
