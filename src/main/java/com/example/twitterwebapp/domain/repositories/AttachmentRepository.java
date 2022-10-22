package com.example.twitterwebapp.domain.repositories;

import com.example.twitterwebapp.domain.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
