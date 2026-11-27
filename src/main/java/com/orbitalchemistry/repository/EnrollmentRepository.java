package com.orbitalchemistry.repository;

import com.orbitalchemistry.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByUserEmailAndCourseId(String email, Long courseId);
    void deleteByCourseId(Long courseId);
    List<Enrollment> findByUserEmail(String email);
}
