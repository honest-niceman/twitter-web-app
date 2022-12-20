package com.example.twitterwebapp.web.controllers.domain;

import com.example.twitterwebapp.domain.dtos.UserDto;
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

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
@Secured("ROLE_ADMIN")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserController(UserService userService,
                          UserMapper userMapper,
                          PasswordEncoder passwordEncoder,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @GetMapping("/find-all")
    public List<UserDto> findAll(@RequestParam int pageNumber,
                                 @RequestParam int pageSize) {
        List<User> users = userService.findAll(pageNumber, pageSize);
        return users.stream().map(userMapper::userToUserDto).toList();
    }

    @PostMapping("/save")
    public UserDto save(@RequestBody UserDto dto) {
        if (userService.existsByUsername(dto.getUsername())) {
            throw new EntityExistsException();
        }
        User user = User.builder()
                        .username(dto.getUsername())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .email(dto.getEmail())
                        .build();
        user.setRole(dto.getRole().toLowerCase().contains("admin") ? Role.ADMIN : Role.USER);
        return userMapper.userToUserDto(userService.save(user));
    }

    @GetMapping("/find")
    public UserDto findById(@RequestParam Long id) {
        User user = userService.findById(id);
        return userMapper.userToUserDto(user);
    }

    @DeleteMapping("/delete")
    public void deleteById(@RequestParam Long id) {
        userService.deleteById(id);
    }

    @PostMapping("/update")
    public UserDto update(@RequestBody UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow();
        User updatedUser = userMapper.updateUserFromUserDto(userDto, user);
        return userMapper.userToUserDto(userRepository.save(updatedUser));
    }
}
