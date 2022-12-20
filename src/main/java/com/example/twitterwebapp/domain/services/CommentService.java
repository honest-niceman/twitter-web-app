package com.example.twitterwebapp.domain.services;

import com.example.twitterwebapp.domain.entities.Comment;
import com.example.twitterwebapp.domain.repositories.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll(Integer pageNumber, Integer pageSize, Long postId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Comment> comments = commentRepository.findAllCommentByPostId(postId, pageable);
        return comments.getContent();
    }

    public Comment save(Comment entity) {
        return commentRepository.save(entity);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    public long count(Long id) {
        return commentRepository.countByUser_Id(id);
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
