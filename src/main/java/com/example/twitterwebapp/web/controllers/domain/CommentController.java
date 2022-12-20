package com.example.twitterwebapp.web.controllers.domain;

import com.example.twitterwebapp.domain.dtos.CommentWithPostDto;
import com.example.twitterwebapp.domain.entities.Comment;
import com.example.twitterwebapp.domain.entities.Role;
import com.example.twitterwebapp.domain.entities.User;
import com.example.twitterwebapp.domain.mappers.CommentMapper;
import com.example.twitterwebapp.domain.repositories.PostRepository;
import com.example.twitterwebapp.domain.services.CommentService;
import com.example.twitterwebapp.domain.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final UserService userService;
    private final PostRepository postRepository;

    public CommentController(CommentService commentService,
                             CommentMapper commentMapper,
                             UserService userService,
                             PostRepository postRepository) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @GetMapping("/find-all")
    public List<CommentWithPostDto> findAll(@RequestParam int pageNumber,
                                            @RequestParam int pageSize,
                                            @RequestParam long postId) {
        List<Comment> comments = commentService.findAll(pageNumber, pageSize, postId);
        return comments.stream().map(commentMapper::toDto).toList();
    }

    @PostMapping("/save")
    public CommentWithPostDto save(@RequestBody CommentWithPostDto dto,
                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        Comment entity = commentMapper.toEntity(dto);
        entity.setDate(LocalDate.now());
        entity.setUser(userService.getCurrentUser(header));
        entity.setPost(postRepository.findById(dto.getPostId()).orElseThrow());
        return commentMapper.toDto(commentService.save(entity));
    }

    @GetMapping("/find")
    public CommentWithPostDto findById(@RequestParam Long id,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        Comment comment = commentService.findById(id);
        User currentUser = userService.getCurrentUser(header);
        if (!(comment.getUser().equals(currentUser) || currentUser.getRole().equals(Role.ADMIN))) {
            throw new IllegalArgumentException("You are not allowed to view other users' comments");
        }
        return commentMapper.toDto(comment);
    }

    @GetMapping("/count")
    public long count(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        return commentService.count(userService.getCurrentUser(header).getId());
    }

    @DeleteMapping("/delete")
    public void deleteById(@RequestParam Long id,
                           @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        Comment comment = commentService.findById(id);
        User currentUser = userService.getCurrentUser(header);
        if (!(comment.getUser().equals(currentUser) || currentUser.getRole().equals(Role.ADMIN))) {
            throw new IllegalArgumentException("You are not allowed to delete other users' comments");
        }
        commentService.deleteById(id);
    }
}
