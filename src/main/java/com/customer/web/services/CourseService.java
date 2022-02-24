package com.customer.web.services;

import com.customer.web.entity.Course;
import com.customer.web.entity.Student;
import com.customer.web.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Collection <Course> list() {

        return courseRepository.findAll();
    }

    public Course findCourseById(Integer id) {

        Optional<Course> result = courseRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }

        return null;
    }
}
