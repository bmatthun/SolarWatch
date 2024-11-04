package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByPublicId(UUID publicId);

    Optional<UserEntity> findByUsername(String username);

    void deleteByPublicId(UUID publicId);

    Optional<UserEntity> findUserByUsername(String username);

    int countUserByUsername(String username);
}
