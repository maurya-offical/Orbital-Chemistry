package com.orbitalchemistry.repository;

import com.orbitalchemistry.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByPublishedTrueOrderByIdDesc();
    List<Course> findAllByOrderByIdDesc();
    long countByPublishedTrue();
}
