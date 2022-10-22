package com.example.twitterwebapp.domain.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.example.twitterwebapp.domain.entities.Comment} entity
 */
@Data
public class CommentDto implements Serializable {
    private final Long id;
    private final Long postId;
    private final Long userId;
    private final String text;
    private final LocalDate date;
    private final Long previousCommentId;
}
