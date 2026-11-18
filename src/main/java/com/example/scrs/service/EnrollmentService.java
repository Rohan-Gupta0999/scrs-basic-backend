package com.example.scrs.service;

import com.example.scrs.dto.EnrollmentResponse;
import com.example.scrs.entity.Course;
import com.example.scrs.entity.Enrollment;
import com.example.scrs.entity.User;
import com.example.scrs.repository.CourseRepository;
import com.example.scrs.repository.EnrollmentRepository;
import com.example.scrs.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             CourseRepository courseRepository,
                             UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    // synchronized is sufficient for this single-instance classroom demonstration.
    @Transactional
    public synchronized EnrollmentResponse enroll(Long courseId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        if (enrollmentRepository.findByUser_IdAndCourse_Id(userId, courseId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Already enrolled in this course");
        }
        if (course.getEnrolledCount() >= course.getCapacity()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course is full");
        }

        course.setEnrolledCount(course.getEnrolledCount() + 1);
        courseRepository.save(course);
        Enrollment enrollment = enrollmentRepository.save(new Enrollment(user, course));
        return toResponse(enrollment);
    }

    @Transactional
    public synchronized void cancel(Long courseId, Long userId) {
        Enrollment enrollment = enrollmentRepository.findByUser_IdAndCourse_Id(userId, courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));
        Course course = enrollment.getCourse();
        course.setEnrolledCount(Math.max(0, course.getEnrolledCount() - 1));
        courseRepository.save(course);
        enrollmentRepository.delete(enrollment);
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponse> getForUser(Long userId) {
        return enrollmentRepository.findByUser_Id(userId).stream().map(this::toResponse).toList();
    }

    private EnrollmentResponse toResponse(Enrollment enrollment) {
        return new EnrollmentResponse(enrollment.getId(), enrollment.getUser().getId(),
                enrollment.getCourse().getId(), enrollment.getCourse().getTitle(), enrollment.getEnrolledAt());
    }
}
