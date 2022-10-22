package com.example.twitterwebapp.domain.repositories;

import com.example.twitterwebapp.domain.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    @Query(value = "with recursive r as" +
            "                   (select id, previous_comment_id, text, date, post_id, user_id, 1 as level" +
            "                    from comments" +
            "                    where previous_comment_id = :uuid" +
            "                    union all" +
            "                    select comments.id, comments.previous_comment_id, comments.text, comments.date, comments.post_id, comments.user_id, r.level + 1 as level" +
            "                    from comments join r on comments.previous_comment_id = r.id)" +
            "select * FROM r", nativeQuery = true)
    List<Comment>findByID(@Param("uuid") UUID uuid);
}