package com.example.scrs.dto;

public record RatingResponse(Long id, Long userId, Long courseId, int rating, String comment) {}
