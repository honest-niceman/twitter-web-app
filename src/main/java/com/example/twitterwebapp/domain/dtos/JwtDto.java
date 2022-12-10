package com.example.twitterwebapp.domain.dtos;

import java.io.Serializable;

public record JwtDto(String jwt, Long id, String username, String email, String role) implements Serializable {}
