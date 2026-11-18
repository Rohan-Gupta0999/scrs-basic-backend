package com.example.scrs.controller;

import com.example.scrs.dto.EnrollmentRequest;
import com.example.scrs.dto.EnrollmentResponse;
import com.example.scrs.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/api/courses/{courseId}/enroll")
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentResponse enroll(@PathVariable Long courseId,
                                     @Valid @RequestBody EnrollmentRequest request) {
        return enrollmentService.enroll(courseId, request.userId());
    }

    @DeleteMapping("/api/courses/{courseId}/enroll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long courseId, @RequestParam Long userId) {
        enrollmentService.cancel(courseId, userId);
    }

    @GetMapping("/api/users/{userId}/enrollments")
    public List<EnrollmentResponse> getEnrollments(@PathVariable Long userId) {
        return enrollmentService.getForUser(userId);
    }
}
