package com.example.twitterwebapp.domain.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.example.twitterwebapp.domain.entities.User} entity
 */
@Data
public class UserDto implements Serializable {
    private final Long id;
    private final String username;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private final String firstName;
    private final String lastName;
    private final String bio;
    private final Set<Long> threadIds;
    private final Set<Long> commentIds;
}
