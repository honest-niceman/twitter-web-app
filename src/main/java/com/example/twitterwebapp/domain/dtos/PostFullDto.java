package com.example.twitterwebapp.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link com.example.twitterwebapp.domain.entities.Post} entity
 */
public record PostFullDto(Long id,
                          String text,
                          Long userId,
                          LocalDate date,
                          Set<AttachmentDto> attachments) implements Serializable {
    /**
     * A DTO for the {@link com.example.twitterwebapp.domain.entities.Attachment} entity
     */
    public record AttachmentDto(Long id,
                                String attachmentUrl) implements Serializable {}
}
