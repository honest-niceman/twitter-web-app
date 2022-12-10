package com.example.twitterwebapp.domain.dtos;

import com.example.twitterwebapp.domain.entities.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.example.twitterwebapp.domain.entities.User} entity
 */
@Data
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private String email;
}
