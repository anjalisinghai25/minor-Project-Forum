package com.discussion.forum.service;

import com.discussion.forum.entities.Course;
import com.discussion.forum.repository.CourseRepository;
import com.discussion.forum.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course saveCourse(Course course) {
        try {
            return courseRepository.save(course);
        } catch (Exception e) {
            throw new RuntimeException("Error saving course", e);
        }
    }

    public Course getCourseById(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public void deleteCourse(String id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        try {
            courseRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting course", e);
        }
    }
}
