package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.customexception.InvalidCityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SolarReportControllerAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidCityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidCityHandler(InvalidCityException exception) {
        return exception.getMessage();
    }
}
