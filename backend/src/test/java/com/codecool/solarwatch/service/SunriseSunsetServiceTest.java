package com.codecool.solarwatch.service;

import com.codecool.solarwatch.customexception.InvalidCityException;
import com.codecool.solarwatch.customexception.InvalidSunSetSunRiseException;
import com.codecool.solarwatch.model.*;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunsetSunriseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SunriseSunsetServiceTest {
    @Mock
    CityRepository cityRepository;

    @Mock
    City city;

    @Mock
    SunsetSunriseRepository sunsetSunriseRepository;

    @Mock
    WebClient webClient;

    @InjectMocks
    SunriseSunsetService sunriseSunsetService;

    @Test
    void testGetSolarReportWithExistingCityData() {
        //Arrange
        String sunrise = "06:30";
        String sunset = "17:00";
        String cityName = "Budapest";
        Long id = (long) 1;
        LocalDate date = LocalDate.of(2025, 1, 1);
        SunsetSunriseEntity sunsetSunriseEntity = new SunsetSunriseEntity(city, sunrise, sunset, date);

        when(city.getId()).thenReturn(id);

        when(cityRepository.findByName("Budapest")).thenReturn(Optional.of(city));

        when(sunsetSunriseRepository.findByCity_IdAndDate(id, date)).thenReturn(Optional.of(sunsetSunriseEntity));

        SolarReport expected = new SolarReport(date, sunrise, sunset, "Budapest");

        //Act
        SolarReport result = sunriseSunsetService.getSolarReport(cityName, date);

        //Assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void testGetSolarReportWithNotExistingCityData() {
        //Arrange
        String cityName = "Budapest";
        LocalDate date = LocalDate.of(2025, 1, 1);

        when(cityRepository.findByName(cityName)).thenReturn(Optional.empty());
        //Assert
        assertThrows(InvalidCityException.class, () -> sunriseSunsetService.getSolarReport(cityName, date));
    }

    @Test
    void testGetSolarReportWithExistingCityButMissingSunsetSunriseData() {
        //Arrange
        String cityName = "Budapest";
        LocalDate date = LocalDate.now();
        Long id = 1L;

        when(cityRepository.findByName(cityName)).thenReturn(Optional.of(city));
        when(city.getId()).thenReturn(id);
        when(sunsetSunriseRepository.findByCity_IdAndDate(id, date)).thenReturn(Optional.empty());

        //Assert
        assertThrows(InvalidSunSetSunRiseException.class, () -> sunriseSunsetService.getSolarReport(cityName, date));
    }

    @Test
    void testAddSolarReportWithNewCity() {

    }


    @Test
    void addSolarReport() {
    }

    @Test
    void deleteSolarReport() {
    }

    @Test
    void updateSolarReport() {
    }
}