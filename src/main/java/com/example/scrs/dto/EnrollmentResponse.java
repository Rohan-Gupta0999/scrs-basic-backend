package com.example.scrs.dto;

import java.time.LocalDateTime;

public record EnrollmentResponse(Long id, Long userId, Long courseId, String courseTitle,
                                 LocalDateTime enrolledAt) {}
