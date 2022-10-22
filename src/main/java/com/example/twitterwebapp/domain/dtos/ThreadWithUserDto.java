package com.example.twitterwebapp.domain.dtos;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.twitterwebapp.domain.entities.Thread} entity
 */
@Data
public class ThreadWithUserDto implements Serializable {
    private final Long id;
    private final Long userId;
}
