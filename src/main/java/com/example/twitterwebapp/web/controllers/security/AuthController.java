package com.example.twitterwebapp.web.controllers.security;

import com.example.twitterwebapp.config.security.jwt.JwtTokenUtil;
import com.example.twitterwebapp.config.security.services.UserDetailsImpl;
import com.example.twitterwebapp.domain.dtos.UserCredentials;
import com.example.twitterwebapp.domain.dtos.UserDto;
import com.example.twitterwebapp.domain.dtos.UserRegistrationDto;
import com.example.twitterwebapp.domain.entities.Role;
import com.example.twitterwebapp.domain.entities.User;
import com.example.twitterwebapp.domain.mappers.UserMapper;
import com.example.twitterwebapp.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserCredentials authRequest) {
        try {
            String username = authRequest.getUsername();
            String password = authRequest.getPassword();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
                                                                                                         password);
            Authentication authenticate = authenticationManager.authenticate(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
            String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, accessToken).build();
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationDto> register(@RequestBody UserRegistrationDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            String message = "Entity with username:%s already exists!".formatted(dto.getUsername());
            return ResponseEntity.status(HttpStatus.valueOf(message)).build();
        }
        User user = User.builder().username(dto.getUsername()).firstName(dto.getLastName())
                        .password(passwordEncoder.encode(dto.getPassword())).role(Role.USER).build();
        return ResponseEntity.ok().body(userMapper.userToUserRegistrationDto(userRepository.save(user)));
    }
}
