package com.example.twitterwebapp.domain.services;

import com.example.twitterwebapp.domain.entities.Attachment;
import com.example.twitterwebapp.domain.repositories.AttachmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public Page<Attachment> findAll(Pageable pageable) {
        return attachmentRepository.findAll(pageable);
    }

    public Attachment save(Attachment entity) {
        return attachmentRepository.save(entity);
    }

    public Optional<Attachment> findById(UUID uuid) {
        return attachmentRepository.findById(uuid);
    }

    public long count() {
        return attachmentRepository.count();
    }

    public void deleteById(UUID uuid) {
        attachmentRepository.deleteById(uuid);
    }
}
