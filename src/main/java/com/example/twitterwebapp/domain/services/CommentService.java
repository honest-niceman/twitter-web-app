package com.example.twitterwebapp.domain.services;

import com.example.twitterwebapp.domain.entities.Comment;
import com.example.twitterwebapp.domain.repositories.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    public Comment save(Comment entity) {
        return commentRepository.save(entity);
    }

    public Optional<Comment> findById(UUID uuid) {
        return commentRepository.findById(uuid);
    }

    public long count() {
        return commentRepository.count();
    }

    public void deleteById(UUID uuid) {
        commentRepository.deleteById(uuid);
    }
}
