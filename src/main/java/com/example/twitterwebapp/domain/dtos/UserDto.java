package com.example.twitterwebapp.domain.dtos;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.twitterwebapp.domain.entities.User} entity
 */
@Data
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String role;
    private String email;
}
