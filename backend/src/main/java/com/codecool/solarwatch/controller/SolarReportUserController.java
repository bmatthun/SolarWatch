package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.SolarReport;
import com.codecool.solarwatch.service.SunriseSunsetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/user")
public class SolarReportUserController {

    private final SunriseSunsetService sunriseSunsetService;

    public SolarReportUserController(SunriseSunsetService sunriseSunsetService) {
        this.sunriseSunsetService = sunriseSunsetService;
    }

    @GetMapping("/solarwatch")
    public SolarReport getSolarReport(@RequestParam LocalDate date, @RequestParam(defaultValue = "Budapest") String city) {

        return sunriseSunsetService.getSolarReport(city, date);
    }
}
