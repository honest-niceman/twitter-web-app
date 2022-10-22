package com.example.twitterwebapp.domain.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * A DTO for the {@link com.example.twitterwebapp.domain.entities.Post} entity
 */
@Data
public class PostFullDto implements Serializable {
    private final UUID id;
    private final UUID attachmentId;
    private final String attachmentAttachmentUrl;
    private final String text;
    private final UUID threadId;
    private final Long threadUserId;
    private final LocalDate date;
    private final Set<UUID> commentIds;
}
