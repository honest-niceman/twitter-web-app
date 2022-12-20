package com.example.twitterwebapp.web.controllers.domain;

import com.example.twitterwebapp.domain.dtos.PostFeedDto;
import com.example.twitterwebapp.domain.services.FeedService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/newsfeed")
public class FeedController {
    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public List<PostFeedDto> getFeed() {
        return feedService.generateFeed();
    }
}
