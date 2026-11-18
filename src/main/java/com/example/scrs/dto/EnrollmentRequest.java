package com.example.scrs.dto;

import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(@NotNull Long userId) {}
