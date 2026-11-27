package com.orbitalchemistry.repository;

import com.orbitalchemistry.model.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    boolean existsByUserEmailAndLessonId(String email, Long lessonId);
    void deleteByUserEmailAndLessonId(String email, Long lessonId);
    List<LessonProgress> findByUserEmailAndLessonSectionCourseId(String email, Long courseId);
    long countByUserEmailAndLessonSectionCourseId(String email, Long courseId);
}
