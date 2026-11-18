package com.example.scrs.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CourseRequest(
        @NotBlank String title,
        String description,
        String category,
        String instructor,
        @Min(1) int capacity
) {}
