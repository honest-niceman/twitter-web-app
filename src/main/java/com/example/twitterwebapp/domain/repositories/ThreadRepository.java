package com.example.twitterwebapp.domain.repositories;

import com.example.twitterwebapp.domain.entities.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ThreadRepository extends JpaRepository<Thread, UUID> {
}
