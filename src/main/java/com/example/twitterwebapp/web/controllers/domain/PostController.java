package com.example.twitterwebapp.web.controllers.domain;

import com.example.twitterwebapp.domain.dtos.PostDto;
import com.example.twitterwebapp.domain.entities.Post;
import com.example.twitterwebapp.domain.entities.Role;
import com.example.twitterwebapp.domain.entities.User;
import com.example.twitterwebapp.domain.mappers.PostMapper;
import com.example.twitterwebapp.domain.repositories.PostRepository;
import com.example.twitterwebapp.domain.services.PostService;
import com.example.twitterwebapp.domain.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;
    private final PostRepository postRepository;

    public PostController(PostService postService,
                          PostMapper postMapper,
                          UserService userService,
                          PostRepository postRepository) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @GetMapping("/find-all")
    public List<PostDto> findAll(@RequestParam int pageNumber,
                                 @RequestParam int pageSize,
                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        User currentUser = userService.getCurrentUser(header);
        List<Post> posts = postService.findAll(pageNumber, pageSize, currentUser.getId());
        return posts.stream().map(postMapper::toDto).toList();
    }

    @PostMapping("/save")
    public PostDto save(@RequestBody PostDto dto,
                        @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        Post post = postMapper.toEntity(dto);
        post.setUser(userService.getCurrentUser(header));
        return postMapper.toDto(postService.save(post));
    }

    @GetMapping("/find")
    public ResponseEntity<?> findById(@RequestParam Long id,
                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        Post post = postService.findById(id);
        User currentUser = userService.getCurrentUser(header);
        if (!(post.getUser().equals(currentUser) || currentUser.getRole().equals(Role.ADMIN))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not allowed to view other users' posts");
        }
        return ResponseEntity.ok().body(postMapper.toDto(post));
    }

    @DeleteMapping("/delete")
    public void deleteById(@RequestParam Long id,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        Post post = postService.findById(id);
        User currentUser = userService.getCurrentUser(header);
        if (!(post.getUser().equals(currentUser) || currentUser.getRole().equals(Role.ADMIN))) {
            throw new IllegalArgumentException();
        }
        postService.deleteById(id);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateById(@RequestBody PostDto dto,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        Post post = postService.findById(dto.getId());
        User currentUser = userService.getCurrentUser(header);
        if (!(post.getUser().equals(currentUser) || currentUser.getRole().equals(Role.ADMIN))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("You are not allowed to update other users' posts");
        }
        Post updatedPost = postMapper.partialUpdate(dto, post);
        return ResponseEntity.ok().body(postMapper.toDto(postRepository.save(updatedPost)));
    }
}
