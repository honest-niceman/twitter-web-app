package com.example.twitterwebapp.domain.services;

import com.example.twitterwebapp.domain.entities.Post;
import com.example.twitterwebapp.domain.repositories.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@SpringBootTest
class PostServiceIntegrationTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    @Test
    @Sql(scripts = "insert-posts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "delete-posts.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAll() {
        System.out.println(postRepository.findAll());
        Assertions.assertEquals(3L, postService.findAll(0, 3, 1L).size());
    }

    @Test
    @Sql(scripts = "insert-posts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "delete-posts.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void save() {
        Assertions.assertEquals(3L, postService.count(1L));
        Post post = new Post();
        post.setDate(LocalDate.now());
        post.setText("New Post");
        post.setUser(userService.findById(1L));
        postService.save(post);
        Assertions.assertEquals(4L, postService.count(1L));
    }

    @Test
    @Sql(scripts = "insert-posts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "delete-posts.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findById() {
        Assertions.assertNotNull(postService.findById (2L));
    }

    @Test
    @Sql(scripts = "insert-posts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "delete-posts.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void count() {
        Assertions.assertEquals(3L, postService.count(1L));
    }

    @Test
    @Sql(scripts = "insert-posts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "delete-posts.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteByIdPositive() {
        Assertions.assertEquals(3L, postService.count(1L));
        postService.deleteById (2L);
        Assertions.assertEquals(2L, postService.count(1L));
    }

    @Test
    void deleteByIdNegative() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> postService.deleteById(777L));
    }
}
