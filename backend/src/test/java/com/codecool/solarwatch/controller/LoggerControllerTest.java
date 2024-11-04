package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.model.payload.JwtResponse;
import com.codecool.solarwatch.model.payload.UserRequest;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoggerControllerTest {

    @InjectMocks
    private LoggerController loggerController;

    @Mock
    private OldUserRepository oldUserRepository;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @Mock
    private User userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        UserRequest signUpRequest = new UserRequest();
        signUpRequest.setUsername("testUser");
        signUpRequest.setPassword("password123");

        doNothing().when(oldUserRepository).createUser(any(UserEntity.class));

        ResponseEntity<Void> response = loggerController.createUser(signUpRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(oldUserRepository, times(1)).createUser(any(UserEntity.class));
    }

    @Test
    void testAuthenticateUser_Success() {
        UserRequest loginRequest = new UserRequest();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("password123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(jwtUtils.generateJwtToken(authentication)).thenReturn("mockJwtToken");

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userDetails.getAuthorities()).thenReturn(List.of(() -> "ROLE_USER"));

        ResponseEntity<?> response = loggerController.authenticateUser(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertNotNull(jwtResponse);
        assertEquals("mockJwtToken", jwtResponse.getToken());
        assertEquals("testUser", jwtResponse.getUsername());
        assertTrue(jwtResponse.getAuthorities().contains("ROLE_USER"));
    }

    @Test
    void testMe_Success() {
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String response = loggerController.me();

        assertEquals("Hello testUser", response);
    }

    @Test
    void testPublicEndpoint() {
        String response = loggerController.publicEndpoint();
        assertEquals("Public\n", response);
    }
}
