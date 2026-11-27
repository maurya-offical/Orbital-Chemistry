package com.orbitalchemistry.repository;

import com.orbitalchemistry.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {}
