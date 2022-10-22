package com.example.twitterwebapp.domain.repositories;

import com.example.twitterwebapp.domain.entities.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
    @Query("select t from Thread t order by t.id")
    Page<Thread> finAllOrderById(Pageable pageable);
}
