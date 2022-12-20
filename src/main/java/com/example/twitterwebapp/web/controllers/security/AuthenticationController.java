package com.example.twitterwebapp.web.controllers.security;

import com.example.twitterwebapp.config.security.jwt.JwtUtils;
import com.example.twitterwebapp.config.security.services.UserDetailsImpl;
import com.example.twitterwebapp.domain.dtos.JwtDto;
import com.example.twitterwebapp.domain.dtos.UserDto;
import com.example.twitterwebapp.domain.entities.Role;
import com.example.twitterwebapp.domain.mappers.UserMapper;
import com.example.twitterwebapp.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDto credentials) {
        var token = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities()
                                 .stream()
                                 .map(GrantedAuthority::getAuthority)
                                 .findFirst()
                                 .orElse(null);
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), role);
        return ResponseEntity.ok(jwtDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already exist!");
        }
        if (userDto.getRole() == null) {
            userDto.setRole(Role.USER.toString());
        }
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        userRepository.save(userMapper.userDtoToUser(userDto));
        return ResponseEntity.ok("User registered successfully!");
    }
}
