package com.example.twitterwebapp.domain.services;

import com.example.twitterwebapp.domain.entities.Attachment;
import com.example.twitterwebapp.domain.repositories.AttachmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<Attachment> findById(Long id) {
        return attachmentRepository.findById(id);
    }

    public long count() {
        return attachmentRepository.count();
    }

    public void deleteById(Long id) {
        attachmentRepository.deleteById(id);
    }
}
