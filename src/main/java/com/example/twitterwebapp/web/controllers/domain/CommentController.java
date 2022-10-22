package com.example.twitterwebapp.web.controllers.domain;

import com.example.twitterwebapp.domain.dtos.CommentDto;
import com.example.twitterwebapp.domain.dtos.CommentWithPostAndUserDto;
import com.example.twitterwebapp.domain.entities.Comment;
import com.example.twitterwebapp.domain.mappers.CommentMapper;
import com.example.twitterwebapp.domain.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService,
                            CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/find-all")
    public List<CommentDto> findAll(@RequestParam int pageNumber,
                                    @RequestParam int pageSize) {
        List<Comment> comments = commentService.findAll(pageNumber, pageSize);
        return comments.stream().map(commentMapper::commentToCommentDto).toList();
    }

    @PostMapping("/save")
    public CommentWithPostAndUserDto save(@RequestParam CommentWithPostAndUserDto dto) {
        Comment comment = commentService.save(commentMapper.commentWithPostAndUserDtoToComment(dto));
        return commentMapper.commentToCommentWithPostAndUserDto(comment);
    }

    @GetMapping("/find")
    public CommentDto findById(@RequestParam Long id) {
        Comment comment = commentService.findById(id);
        return commentMapper.commentToCommentDto(comment);
    }

    @GetMapping("/count")
    public long count() {
        return commentService.count();
    }

    @DeleteMapping("/delete")
    public void deleteById(@RequestParam Long id) {
        commentService.deleteById(id);
    }
}
