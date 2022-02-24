package com.customer.web.controllers;

import com.customer.web.controllers.exception.CustomNotFoundException;
import com.customer.web.entity.Course;
import com.customer.web.entity.Instructor;
import com.customer.web.entity.Student;
import com.customer.web.services.CourseService;
import com.customer.web.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public Collection<Course> list() {
        return courseService.list();
    }

    @GetMapping()
    @RequestMapping("/{id}")
    public Course get(@PathVariable Integer id) {
        Course course = courseService.findCourseById(id);
        if (course == null) {
            throw new CustomNotFoundException("Course id not found - " + id);
        }

        return course;
    }

    @PostMapping
    public Course create(@RequestBody final Course course) throws Exception {
        Course duplicateCourse = courseService.findCourseByTitle(course.getTitle());
        if(duplicateCourse != null) {
            throw new Exception("Course with title - " + course.getTitle() + " already exits");
        }
        return courseService.saveCourse(course);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        Course course = courseService.findCourseById(id);
        if (course == null) {
            throw new CustomNotFoundException("Course id not found - " + id);
        }
        courseService.deleteCourse(id);
    }

    @PostMapping
    @RequestMapping("/{id}/instructor/attach")
    public Course attachInstructor(@PathVariable Integer id, @RequestBody Map<String, Object> payload) {
        Course course = courseService.findCourseById(id);
        Integer instructorId = (Integer) payload.get("instructor_id");
        if (course == null) {
            throw new CustomNotFoundException("Course id not found - " + id);
        }
        Instructor instructor = instructorService.findByInstructorId(instructorId);
        if (instructor == null) {
            throw new CustomNotFoundException("Instructor id not found - " + instructorId);
        }

        course.setInstructor(instructor);
        return courseService.saveCourse(course);
    }

    @PostMapping
    @RequestMapping("/{id}/instructor/detach")
    public Course detachInstructor(@PathVariable Integer id, @RequestBody Map<String, Object> payload) {
        Course course = courseService.findCourseById(id);
        Integer instructorId = (Integer) payload.get("instructor_id");
        if (course == null) {
            throw new CustomNotFoundException("Course id not found - " + id);
        }
        Instructor instructor = instructorService.findByInstructorId(instructorId);
        if (instructor == null) {
            throw new CustomNotFoundException("Instructor id not found - " + instructorId);
        }

        course.setInstructor(null);
        return courseService.saveCourse(course);
    }
}
