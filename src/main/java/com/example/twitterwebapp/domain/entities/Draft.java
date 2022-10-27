package com.example.twitterwebapp.domain.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "draft")
public class Draft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "text")
    private String text;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "draft_id")
    @ToString.Exclude
    private Set<Attachment> attachments = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Draft draft = (Draft) o;
        return id != null && Objects.equals(id, draft.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
