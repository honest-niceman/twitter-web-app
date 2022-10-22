package com.example.twitterwebapp.domain.services;

import com.example.twitterwebapp.domain.entities.User;
import com.example.twitterwebapp.domain.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Secured("ROLE_ADMIN")
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> users = userRepository.finAllOrderById(pageable);
        return users.getContent();
    }

    public User save(User entity) {
        return userRepository.save(entity);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public long count() {
        return userRepository.count();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
