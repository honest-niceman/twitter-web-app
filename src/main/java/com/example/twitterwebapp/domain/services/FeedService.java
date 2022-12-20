package com.example.twitterwebapp.domain.services;

import com.example.twitterwebapp.domain.dtos.PostFeedDto;
import com.example.twitterwebapp.domain.entities.Post;
import com.example.twitterwebapp.domain.mappers.PostFeedMapper;
import com.example.twitterwebapp.domain.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {
    private final PostRepository postRepository;
    private final PostFeedMapper postFeedMapper;

    public FeedService(PostRepository postRepository,
                       PostFeedMapper postFeedMapper) {
        this.postRepository = postRepository;
        this.postFeedMapper = postFeedMapper;
    }

    public List<PostFeedDto> generateFeed() {
        List<Post> postsWithUsers = postRepository.findPostsWithUsers();
        return postFeedMapper.toDto(postsWithUsers);
    }
}
