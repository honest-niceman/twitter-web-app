package com.example.twitterwebapp.domain.services;

import com.example.twitterwebapp.domain.entities.Thread;
import com.example.twitterwebapp.domain.repositories.ThreadRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Thread findById(Long id) {
        return threadRepository.findById(id).orElseThrow();
    }

    public long count() {
        return threadRepository.count();
    }

    public void deleteById(Long id) {
        threadRepository.deleteById(id);
    }
}
