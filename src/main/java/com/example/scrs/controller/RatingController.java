package com.example.scrs.controller;

import com.example.scrs.dto.RatingRequest;
import com.example.scrs.dto.RatingResponse;
import com.example.scrs.entity.Course;
import com.example.scrs.entity.Rating;
import com.example.scrs.entity.User;
import com.example.scrs.repository.CourseRepository;
import com.example.scrs.repository.RatingRepository;
import com.example.scrs.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/ratings")
public class RatingController {

    private final RatingRepository ratingRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public RatingController(RatingRepository ratingRepository, CourseRepository courseRepository,
                            UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RatingResponse addRating(@PathVariable Long courseId,
                                    @Valid @RequestBody RatingRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        Rating rating = ratingRepository.findByUser_IdAndCourse_Id(user.getId(), courseId)
                .orElse(new Rating(user, course, request.rating(), request.comment()));
        rating.setRating(request.rating());
        rating.setComment(request.comment());
        rating = ratingRepository.save(rating);
        return new RatingResponse(rating.getId(), user.getId(), courseId,
                rating.getRating(), rating.getComment());
    }

    @GetMapping
    public List<RatingResponse> getRatings(@PathVariable Long courseId) {
        return ratingRepository.findByCourse_Id(courseId).stream()
                .map(r -> new RatingResponse(r.getId(), r.getUser().getId(),
                        r.getCourse().getId(), r.getRating(), r.getComment()))
                .toList();
    }
}
