package com.example.scrs.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RatingRequest(
        @NotNull Long userId,
        @Min(1) @Max(5) int rating,
        String comment
) {}
