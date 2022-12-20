package com.example.twitterwebapp.domain.repositories;

import com.example.twitterwebapp.domain.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p " +
        "join fetch User u on p.user = u " +
        "join fetch Attachment a on p.attachment = a")
    List<Post> findPostsWithUsers();

    @Query("select p from Post p where p.user.id = :id order by p.id")
    Page<Post> finAllOrderById(Pageable pageable,
                               Long id);

    long countByUser_Id(Long id);
}
