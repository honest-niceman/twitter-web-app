package com.example.twitterwebapp.domain.services;

import com.example.twitterwebapp.domain.entities.Post;
import com.example.twitterwebapp.domain.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> posts = postRepository.finAllOrderById(pageable);
        return posts.getContent();
    }

    public Post save(Post entity) {
        return postRepository.save(entity);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    public long count() {
        return postRepository.count();
    }

    public void deleteById(Long id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("There is no entity with id=%s".formatted(id));
        }
        postRepository.deleteById(id);
    }
}
