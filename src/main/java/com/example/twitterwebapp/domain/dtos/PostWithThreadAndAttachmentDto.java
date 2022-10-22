package com.example.twitterwebapp.domain.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.example.twitterwebapp.domain.entities.Post} entity
 */
@Data
public class PostWithThreadAndAttachmentDto implements Serializable {
    private final String text;
    private final Long threadId;
    private final LocalDate date;
    private final Long attachmentId;
    private final String attachmentAttachmentUrl;
}
