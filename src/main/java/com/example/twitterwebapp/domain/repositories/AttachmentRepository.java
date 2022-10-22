package com.example.twitterwebapp.domain.repositories;

import com.example.twitterwebapp.domain.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}
