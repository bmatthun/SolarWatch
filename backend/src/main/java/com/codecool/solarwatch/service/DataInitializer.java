package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initData() {
        System.out.println("before");
        if (userRepository.findUserByUsername("admin").isEmpty()) {
            UserEntity admin = new UserEntity("admin", passwordEncoder.encode("admin"), List.of(Role.ROLE_USER, Role.ROLE_ADMIN));

            userRepository.save(admin);
            System.out.println("Admin added");
        }
    }
}
