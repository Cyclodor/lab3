package com.example.studentapi.service;

import com.example.studentapi.model.Course;
import com.example.studentapi.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CacheService cacheService;
    public List<Course> getAllCourses() {
        String cacheKey = "all_courses";
        if (cacheService.containsKey(cacheKey)) {
            return (List<Course>) cacheService.get(cacheKey);
        }
        List<Course> courses = courseRepository.findAll();
        cacheService.put(cacheKey, courses);
        return courses;
    }
    public Optional<Course> getCourseById(Long id) {
        String cacheKey = "course_" + id;
        if (cacheService.containsKey(cacheKey)) {
            return Optional.ofNullable((Course) cacheService.get(cacheKey));
        }
        Optional<Course> course = courseRepository.findById(id);
        course.ifPresent(c -> cacheService.put(cacheKey, c));
        return course;
    }
    public Course saveCourse(Course course) {
        Course saved = courseRepository.save(course);
        cacheService.put("course_" + saved.getId(), saved);
        cacheService.remove("all_courses");
        return saved;
    }
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
        cacheService.remove("course_" + id);
        cacheService.remove("all_courses");
    }
    public List<Course> getCoursesByNameContaining(String courseName) {
        String cacheKey = "courses_by_name_" + courseName;
        if (cacheService.containsKey(cacheKey)) {
            return (List<Course>) cacheService.get(cacheKey);
        }
        List<Course> courses = courseRepository.findCoursesByNameContaining(courseName);
        cacheService.put(cacheKey, courses);
        return courses;
    }
} 