package com.example.twitterwebapp.web.controllers.domain;

import com.example.twitterwebapp.domain.dtos.CommentWithPostDto;
import com.example.twitterwebapp.domain.entities.Comment;
import com.example.twitterwebapp.domain.entities.Role;
import com.example.twitterwebapp.domain.entities.User;
import com.example.twitterwebapp.domain.mappers.CommentMapper;
import com.example.twitterwebapp.domain.services.CommentService;
import com.example.twitterwebapp.domain.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@Secured("ROLE_USER")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final UserService userService;

    public CommentController(CommentService commentService,
                             CommentMapper commentMapper,
                             UserService userService) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
        this.userService = userService;
    }

    @GetMapping("/find-all")
    public List<CommentWithPostDto> findAll(@RequestParam int pageNumber,
                                            @RequestParam int pageSize,
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        User currentUser = userService.getCurrentUser(header);
        List<Comment> comments = commentService.findAll(pageNumber, pageSize, currentUser.getId());
        return comments.stream().map(commentMapper::toDto).toList();
    }

    @PostMapping("/save")
    public CommentWithPostDto save(@RequestBody CommentWithPostDto dto,
                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        Comment entity = commentMapper.toEntity(dto);
        entity.setUser(userService.getCurrentUser(header));
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
