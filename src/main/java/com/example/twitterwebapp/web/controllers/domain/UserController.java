package com.example.twitterwebapp.web.controllers.domain;

import com.example.twitterwebapp.domain.dtos.UserDto;
import com.example.twitterwebapp.domain.dtos.UserRegistrationDto;
import com.example.twitterwebapp.domain.entities.Role;
import com.example.twitterwebapp.domain.entities.User;
import com.example.twitterwebapp.domain.mappers.UserMapper;
import com.example.twitterwebapp.domain.repositories.UserRepository;
import com.example.twitterwebapp.domain.services.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Secured("ROLE_ADMIN")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                            UserMapper userMapper,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/find-all")
    public List<UserDto> findAll(@RequestParam int pageNumber,
                                 @RequestParam int pageSize) {
        List<User> users = userService.findAll(pageNumber, pageSize);
        return users.stream().map(userMapper::userToUserDto).toList();
    }

    @PostMapping("/save")
    public UserRegistrationDto save(@RequestParam UserRegistrationDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new EntityExistsException();
        }
        User user = User.builder().username(dto.getUsername()).firstName(dto.getLastName())
                        .password(passwordEncoder.encode(dto.getPassword())).role(Role.ADMIN).build();
        return userMapper.userToUserRegistrationDto(userService.save(user));
    }

    @GetMapping("/find")
    public UserDto findById(@RequestParam Long id) {
        User user = userService.findById(id);
        return userMapper.userToUserDto(user);
    }

    @GetMapping("/count")
    public long count() {
        return userService.count();
    }

    @DeleteMapping("/delete")
    public void deleteById(@RequestParam Long id) {
        userService.deleteById(id);
    }
}
