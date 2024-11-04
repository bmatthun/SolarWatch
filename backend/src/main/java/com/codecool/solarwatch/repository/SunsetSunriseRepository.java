package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.SunsetSunriseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SunsetSunriseRepository extends JpaRepository<SunsetSunriseEntity, Long> {

    Optional<SunsetSunriseEntity> findByCity_IdAndDate(Long cityId, LocalDate date);
    boolean deleteByCity_IdAndDate(Long cityId, LocalDate date);
}

