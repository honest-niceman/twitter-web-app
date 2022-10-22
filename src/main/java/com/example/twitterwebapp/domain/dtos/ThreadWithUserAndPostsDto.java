package com.example.twitterwebapp.domain.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link com.example.twitterwebapp.domain.entities.Thread} entity
 */
@Data
public class ThreadWithUserAndPostsDto implements Serializable {
    private final Long id;
    private final Long userId;
    private final Set<PostDto> posts;

    /**
     * A DTO for the {@link com.example.twitterwebapp.domain.entities.Post} entity
     */
    @Data
    public static class PostDto implements Serializable {
        private final Long id;
        private final String text;
        private final LocalDate date;
    }
}
