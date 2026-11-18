package com.example.scrs.repository;

import com.example.scrs.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByCourse_Id(Long courseId);
    Optional<Rating> findByUser_IdAndCourse_Id(Long userId, Long courseId);
}
