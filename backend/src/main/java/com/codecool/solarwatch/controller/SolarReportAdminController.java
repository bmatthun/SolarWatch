package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.SolarReport;
import com.codecool.solarwatch.model.SolarUpdateRequest;
import com.codecool.solarwatch.model.SunsetSunriseEntity;
import com.codecool.solarwatch.service.SunriseSunsetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin")
public class SolarReportAdminController {

    private final SunriseSunsetService sunriseSunsetService;

    public SolarReportAdminController(SunriseSunsetService sunriseSunsetService) {
        this.sunriseSunsetService = sunriseSunsetService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public SolarReport addSolarReport(@RequestParam LocalDate date, @RequestParam(defaultValue = "Budapest") String city) {

        return sunriseSunsetService.addSolarReport(city, date);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteSolarReport(@RequestParam(required = false) LocalDate date, @RequestParam(defaultValue = "Budapest") String city) {
        if (date == null) {
            date = LocalDate.now();
        }
        return sunriseSunsetService.deleteSolarReport(city, date);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public SunsetSunriseEntity updateSolarReport(@RequestParam(required = false) LocalDate date, @RequestParam(defaultValue = "Budapest") String city, @RequestBody SolarUpdateRequest solarUpdateRequest) {
        if (date == null) {
            date = LocalDate.now();
        }
        return sunriseSunsetService.updateSolarReport(city, date, solarUpdateRequest);
    }
}
