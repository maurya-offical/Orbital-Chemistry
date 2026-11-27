package com.orbitalchemistry.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.Instant;

@Entity
@Table(name = "lesson_progress", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "lesson_id"}))
public class LessonProgress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "user_id") public User user;
    /** ON DELETE CASCADE: deleting a lesson (or course) in the admin panel also clears its progress rows. */
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "lesson_id")
    @OnDelete(action = OnDeleteAction.CASCADE) public Lesson lesson;
    public Instant completedAt = Instant.now();
}
