package com.codecool.solarwatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class SunsetSunriseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private City city;
    private String sunrise;
    private String sunset;
    private LocalDate date;

    public SunsetSunriseEntity(City city, String sunrise, String sunset, LocalDate date) {
        this.city = city;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.date = date;
    }

    public SunsetSunriseEntity() {

    }

    public LocalDate getDate() {
        return date;
    }

    public City getCity() {
        return city;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
