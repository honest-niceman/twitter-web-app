package com.example.twitterwebapp.domain.repositories;

import com.example.twitterwebapp.domain.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.post.id = ?1 order by c.post.id")
    Page<Comment> findAllCommentByPostId(Long id, Pageable pageable);

    long countByUser_Id(Long id);
}
