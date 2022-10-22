package com.example.twitterwebapp.web.controllers.domain;

import com.example.twitterwebapp.config.security.jwt.JwtTokenUtil;
import com.example.twitterwebapp.domain.dtos.ThreadWithUserAndPostsDto;
import com.example.twitterwebapp.domain.dtos.ThreadWithUserDto;
import com.example.twitterwebapp.domain.entities.Role;
import com.example.twitterwebapp.domain.entities.Thread;
import com.example.twitterwebapp.domain.entities.User;
import com.example.twitterwebapp.domain.mappers.ThreadMapper;
import com.example.twitterwebapp.domain.services.ThreadService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/thread")
public class ThreadController {
    private final ThreadService threadService;
    private final ThreadMapper threadMapper;
    private final JwtTokenUtil jwtTokenUtil;

    public ThreadController(ThreadService threadService,
                          ThreadMapper threadMapper,
                            JwtTokenUtil jwtTokenUtil) {
        this.threadService = threadService;
        this.threadMapper = threadMapper;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/find-all")
    public List<ThreadWithUserAndPostsDto> findAll(@RequestParam int pageNumber,
                                                   @RequestParam int pageSize) {
        List<Thread> threads = threadService.findAll(pageNumber, pageSize);
        return threads.stream().map(threadMapper::threadToThreadWithUserAndPostsDto).toList();
    }

    @PostMapping("/save")
    public ThreadWithUserDto save(@RequestParam ThreadWithUserDto dto) {
        Thread thread = threadService.save(threadMapper.threadWithUserDtoToThread(dto));
        return threadMapper.threadToThreadWithUserDto(thread);
    }

    @GetMapping("/find")
    public ThreadWithUserAndPostsDto findById(@RequestParam Long id) {
        Thread thread = threadService.findById(id);
        return threadMapper.threadToThreadWithUserAndPostsDto(thread);
    }

    @GetMapping("/count")
    public long count() {
        return threadService.count();
    }

    @DeleteMapping("/delete")
    public void deleteById(@RequestParam Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        final String token = header.split(" ")[1].trim();
        String tokenUsername = jwtTokenUtil.getUsername(token);
        User user = threadService.findById(id).getUser();
        if (!user.getUsername().equals(tokenUsername) && !user.getRole().equals(Role.ADMIN)) {
            throw new IllegalArgumentException("You can delete only records that you have created");
        }
        threadService.deleteById(id);
    }
}
