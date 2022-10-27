package com.example.twitterwebapp.domain.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link com.example.twitterwebapp.domain.entities.Post} entity
 */
@Data
public class PostWithAttachmentDto implements Serializable {
    private final Long id;
    private final String text;
    private final LocalDate date;
    private final Set<AttachmentDto> attachments;

    /**
     * A DTO for the {@link com.example.twitterwebapp.domain.entities.Attachment} entity
     */
    @Data
    public static class AttachmentDto implements Serializable {
        private final Long id;
        private final String attachmentUrl;
    }
}
