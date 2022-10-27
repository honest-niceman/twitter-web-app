package com.example.twitterwebapp.web.controllers.domain;

import com.example.twitterwebapp.domain.dtos.PostFullDto;
import com.example.twitterwebapp.domain.dtos.PostWithAttachmentDto;
import com.example.twitterwebapp.domain.entities.Post;
import com.example.twitterwebapp.domain.entities.Role;
import com.example.twitterwebapp.domain.entities.User;
import com.example.twitterwebapp.domain.mappers.PostMapper;
import com.example.twitterwebapp.domain.services.PostService;
import com.example.twitterwebapp.domain.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    public PostController(PostService postService,
                          PostMapper postMapper,
                          UserService userService) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.userService = userService;
    }

    @GetMapping("/find-all")
    public List<PostFullDto> findAll(@RequestParam int pageNumber,
                                     @RequestParam int pageSize,
                                     @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        User currentUser = userService.getCurrentUser(header);
        List<Post> posts = postService.findAll(pageNumber, pageSize, currentUser.getId());
        return posts.stream().map(postMapper::postToPostFullDto).toList();
    }

    @PostMapping("/save")
    public PostFullDto save(@RequestBody PostWithAttachmentDto dto,
                            @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        Post post = postMapper.postWithAttachmentDtoToPost(dto);
        post.setUser(userService.getCurrentUser(header));
        return postMapper.postToPostFullDto(postService.save(post));
    }

    @GetMapping("/find")
    public ResponseEntity<?> findById(@RequestParam Long id,
                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        Post post = postService.findById(id);
        User currentUser = userService.getCurrentUser(header);
        if (!(post.getUser().equals(currentUser) || currentUser.getRole().equals(Role.ADMIN))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("You are not allowed to view other users' posts");
        }
        return ResponseEntity.ok().body(postMapper.postToPostFullDto(post));
    }

    @GetMapping("/count")
    public long count(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        return postService.count(userService.getCurrentUser(header).getId());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteById(@RequestParam Long id,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        Post post = postService.findById(id);
        User currentUser = userService.getCurrentUser(header);
        if (!(post.getUser().equals(currentUser) || currentUser.getRole().equals(Role.ADMIN))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("You are not allowed to delete other users' posts");
        }
        postService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("An entity with id=%d was successfully deleted".formatted(id));
    }
}
