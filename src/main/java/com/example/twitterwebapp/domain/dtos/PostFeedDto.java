package com.example.twitterwebapp.domain.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.example.twitterwebapp.domain.entities.Post} entity
 */
@Data
public class PostFeedDto implements Serializable {
    private final Long id;
    private final String text;
    private final String username;
    private final String userPhoto;
    private final LocalDate date;
    private final String attachmentUrl;
}
