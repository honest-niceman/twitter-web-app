package com.example.twitterwebapp.web.controllers.domain;

import com.example.twitterwebapp.domain.dtos.PostFullDto;
import com.example.twitterwebapp.domain.dtos.PostWithThreadDto;
import com.example.twitterwebapp.domain.entities.Post;
import com.example.twitterwebapp.domain.mappers.PostMapper;
import com.example.twitterwebapp.domain.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService,
                          PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping("/find-all")
    public List<PostFullDto> findAll(@RequestParam int pageNumber,
                                     @RequestParam int pageSize) {
        List<Post> posts = postService.findAll(pageNumber, pageSize);
        return posts.stream().map(postMapper::postToPostFullDto).toList();
    }

    @PostMapping("/save")
    public PostFullDto save(@RequestParam PostWithThreadDto dto) {
        Post post = postService.save(postMapper.postWithThreadDtoToPost(dto));
        return postMapper.postToPostFullDto(post);
    }

    @GetMapping("/find")
    public PostFullDto findById(@RequestParam UUID id) {
        Post post = postService.findById(id);
        return postMapper.postToPostFullDto(post);
    }

    @GetMapping("/count")
    public long count() {
        return postService.count();
    }

    @DeleteMapping("/delete")
    public void deleteById(@RequestParam UUID uuid) {
        postService.deleteById(uuid);
    }
}
