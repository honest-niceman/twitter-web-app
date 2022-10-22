package com.example.twitterwebapp.domain.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A DTO for the {@link com.example.twitterwebapp.domain.entities.Post} entity
 */
@Data
public class PostWithThreadDto implements Serializable {
    private final String text;
    private final UUID threadId;
    private final LocalDate date;
}
