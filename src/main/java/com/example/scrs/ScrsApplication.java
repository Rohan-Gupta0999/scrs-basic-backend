package com.example.scrs;

import com.example.scrs.entity.Course;
import com.example.scrs.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScrsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrsApplication.class, args);
    }

    // Add a few courses automatically so the API can be tested immediately.
    @Bean
    CommandLineRunner addSampleCourses(CourseRepository courseRepository) {
        return args -> {
            if (courseRepository.count() == 0) {
                courseRepository.save(new Course(
                        "Introduction to Python",
                        "Learn Python fundamentals, variables, loops and functions.",
                        "Programming", "NIET Faculty", 50));
                courseRepository.save(new Course(
                        "Data Structures and Algorithms",
                        "Learn arrays, linked lists, stacks, queues and basic algorithms.",
                        "Computer Science", "NIET Faculty", 40));
                courseRepository.save(new Course(
                        "Machine Learning Basics",
                        "An introduction to supervised and unsupervised machine learning.",
                        "AI/ML", "NIET Faculty", 35));
                courseRepository.save(new Course(
                        "Web Development with Java",
                        "Build basic web applications using Java and Spring Boot.",
                        "Web Development", "NIET Faculty", 30));
            }
        };
    }
}
