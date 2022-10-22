package com.example.twitterwebapp.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "thread")
@Getter
@Setter
@ToString
public class Thread {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "thread", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Post> posts = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Thread thread = (Thread) o;
        return id != null && Objects.equals(id, thread.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
