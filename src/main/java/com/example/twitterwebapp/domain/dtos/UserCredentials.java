package com.example.twitterwebapp.domain.dtos;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.twitterwebapp.domain.entities.User} entity
 */
@Data
public class UserCredentials implements Serializable {
    private final String password;
    private final String username;
}
