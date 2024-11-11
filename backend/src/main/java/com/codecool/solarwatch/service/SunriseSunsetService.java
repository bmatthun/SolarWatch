package com.codecool.solarwatch.service;

import com.codecool.solarwatch.customexception.InvalidCityException;
import com.codecool.solarwatch.customexception.InvalidSunSetSunRiseException;
import com.codecool.solarwatch.model.*;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunsetSunriseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SunriseSunsetService {

    //feat: implement logger!
    private static final String API_KEY = "eb4bf9cea5d76f4322a879ac6a0cc576";  // Replace with your actual API key
    private final CityRepository cityRepository;
    private final SunsetSunriseRepository sunsetSunriseRepository;
    private final WebClient webClient;
    private final Logger logger = LoggerFactory.getLogger(SunriseSunsetService.class);

    public SunriseSunsetService(CityRepository cityRepository, SunsetSunriseRepository sunsetSunriseRepository, WebClient webClient) {
        this.cityRepository = cityRepository;
        this.sunsetSunriseRepository = sunsetSunriseRepository;
        this.webClient = webClient;
    }

    //USER
    //fix: return properly Optional if empty
    public SolarReport getSolarReport(String city, LocalDate date) {
        logger.info("Fetching solar report for city: {} on date: {}", city, date);

        City city1 = getCity(city);
        logger.debug("City found: {}", city1);

        Optional<SunsetSunriseEntity> optionalSunsetSunriseEntity = sunsetSunriseRepository.findByCity_IdAndDate(city1.getId(), date);

        if (optionalSunsetSunriseEntity.isPresent()) {
            return new SolarReport(date, optionalSunsetSunriseEntity.get().getSunrise(), optionalSunsetSunriseEntity.get().getSunset(), city);
        } else {
            throw new InvalidSunSetSunRiseException();
        }
    }

    private Optional<City> getCityFromDatabase(String cityName) {
        return cityRepository.findByName(cityName);
    }

    private City getCity(String cityName) {
        Optional<City> optionalCity = getCityFromDatabase(cityName);
        return optionalCity.orElseThrow(InvalidCityException::new);
    }

    //ADMIN
    //add solarreport to DB
    public SolarReport addSolarReport(String city, LocalDate date) {
        City city1 = checkCity(city);

        Optional<SunsetSunriseEntity> optionalSunsetSunriseEntity = sunsetSunriseRepository.findByCity_IdAndDate(city1.getId(), date);

        if (optionalSunsetSunriseEntity.isPresent()) {
            return new SolarReport(date, optionalSunsetSunriseEntity.get().getSunrise(), optionalSunsetSunriseEntity.get().getSunset(), city);
        } else {
            String url = String.format("https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=%s", city1.getLatitude(), city1.getLongitude(), date.toString());

            SunriseSunsetResults response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(SunriseSunsetResults.class)
                    .block();

            logger.info("Response from the Weather Forecast API: {}", response);

            String sunrise = response.results().sunrise();
            String sunset = response.results().sunset();

            //save sunset-sunrise into DB
            sunsetSunriseRepository.save(new SunsetSunriseEntity(city1, sunrise, sunset, date));

            return new SolarReport(date, sunrise, sunset, city);
        }
    }

    //check and add city to DB
    private City checkCity(String cityName) {
        Optional<City> optionalCity = getCityFromDatabase(cityName);

        if (optionalCity.isPresent()) {
            return optionalCity.get();
        } else {
            double lat = getGeocoding(cityName).lat();
            double lon = getGeocoding(cityName).lon();
            String country = getGeocoding(cityName).country();

            //saving the data in the DB
            City city = cityRepository.save(new City(cityName, lat, lon, country));
            return city;
        }
    }

    private Geocoding getGeocoding(String city) {
        String url = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s", city, API_KEY);

        Geocoding response = webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(Geocoding[].class)
                .map(array -> array[0])
                .block();

        logger.info("Response from the Geocoding API: {}", response);

        if (response != null) {
            return response;
        } else {
            throw new InvalidCityException();
        }
    }

    public boolean deleteSolarReport(String city, LocalDate date) {
        City city1 = checkCity(city);
        return sunsetSunriseRepository.deleteByCity_IdAndDate(city1.getId(), date);
    }

    public SunsetSunriseEntity updateSolarReport(String city, LocalDate date, SolarUpdateRequest request) {
        City city1 = getCity(city);
        return sunsetSunriseRepository.save(new SunsetSunriseEntity(city1, request.getSunrise(), request.getSunset(), date));
    }
}
