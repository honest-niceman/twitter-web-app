package com.example.twitterwebapp.domain.repositories;

import com.example.twitterwebapp.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    //Возвращает список пользователей, у которых
    //фамилия начинается с заданной последовательности символов
    @Query("select u from User u " +
            "where upper(u.lastName) like upper(concat(:lastName, '%')) " +
            "order by u.username")
    List<User> findByLastNameStartsWith(@Param("lastName") String lastName);

    //Возвращает список пользователей,
    // у которых есть посты в заданную дату
    @Query("select u from User u " +
            "inner join Thread t on t.user.id = u.id " +
            "inner join Post p on p.thread.id = t.id " +
            "where p.date=:date " +
            "group by u")
    List<User> findUsersThatPosts(@Param("date") LocalDate date);
}
