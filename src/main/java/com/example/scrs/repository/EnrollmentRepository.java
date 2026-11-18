package com.example.scrs.repository;

import com.example.scrs.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUser_Id(Long userId);
    Optional<Enrollment> findByUser_IdAndCourse_Id(Long userId, Long courseId);
}
