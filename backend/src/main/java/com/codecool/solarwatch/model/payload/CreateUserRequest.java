package com.codecool.solarwatch.model.payload;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
}
