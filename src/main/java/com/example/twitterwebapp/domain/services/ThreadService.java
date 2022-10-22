package com.example.twitterwebapp.domain.services;

import com.example.twitterwebapp.domain.entities.Thread;
import com.example.twitterwebapp.domain.repositories.ThreadRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ThreadService {
    private final ThreadRepository threadRepository;

    public ThreadService(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public Page<Thread> findAll(Pageable pageable) {
        return threadRepository.findAll(pageable);
    }

    public Thread save(Thread entity) {
        return threadRepository.save(entity);
    }

    public Optional<Thread> findById(UUID uuid) {
        return threadRepository.findById(uuid);
    }

    public long count() {
        return threadRepository.count();
    }

    public void deleteById(UUID uuid) {
        threadRepository.deleteById(uuid);
    }
}
