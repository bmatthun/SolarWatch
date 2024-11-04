package com.codecool.solarwatch.model.payload;

import java.util.List;

public record JwtResponse(String jwt, String userName, List<String> roles) {

    public String getToken() {
        return jwt;
    }

    public String getUsername() {
        return userName;
    }

    public List<String> getAuthorities() {
        return roles;
    }
}
