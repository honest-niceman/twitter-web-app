package com.example.twitterwebapp.domain.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link com.example.twitterwebapp.domain.entities.Post} entity
 */
@Data
public class PostFullDto implements Serializable {
    private final Long id;
    private final Long attachmentId;
    private final String attachmentAttachmentUrl;
    private final String text;
    private final Long threadId;
    private final Long threadUserId;
    private final LocalDate date;
    private final Set<Long> commentIds;
}
