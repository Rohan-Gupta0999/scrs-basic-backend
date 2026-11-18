package com.example.scrs.controller;

import com.example.scrs.dto.CourseRequest;
import com.example.scrs.entity.Course;
import com.example.scrs.repository.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course addCourse(@Valid @RequestBody CourseRequest request) {
        Course course = new Course(request.title(), request.description(), request.category(),
                request.instructor(), request.capacity());
        return courseRepository.save(course);
    }
}
