package com.example.twitterwebapp.domain.services;

import com.example.twitterwebapp.config.security.jwt.JwtTokenUtil;
import com.example.twitterwebapp.domain.entities.User;
import com.example.twitterwebapp.domain.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;

@Service
@Secured("ROLE_ADMIN")
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public UserService(UserRepository userRepository,
                       JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public List<User> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> users = userRepository.finAllOrderById(pageable);
        return users.getContent();
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
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

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public User getCurrentUser(String header) {
        final String token = header.split(" ")[1].trim();
        String tokenUsername = jwtTokenUtil.getUsername(token);
        return findByUsername(tokenUsername);
    }
}
