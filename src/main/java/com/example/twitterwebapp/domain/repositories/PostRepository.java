package com.example.twitterwebapp.domain.repositories;

import com.example.twitterwebapp.domain.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p, p.text from Post p " +
            "group by p " +
            "having upper(p.text) like upper(concat('%', :word, '%'))")
    List<Post> findByCommentsContainsBannedWord(@Param("word") String word);

    @Query("select p from Post p where p.user.id = :id order by p.id")
    Page<Post> finAllOrderById(Pageable pageable, Long id);

    long countByUser_Id(Long id);
}
