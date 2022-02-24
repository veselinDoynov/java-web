package com.customer.web.controllers;

import com.customer.web.controllers.exception.CustomNotFoundException;

import com.customer.web.entity.Course;
import com.customer.web.entity.Student;
import com.customer.web.services.CourseService;
import com.customer.web.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public Collection<Student> list() {
        return studentService.list();
    }

    @GetMapping()
    @RequestMapping("/{id}")
    public Student get(@PathVariable Integer id) {
        Student student = studentService.findStudentById(id);
        if (student == null) {
            throw new CustomNotFoundException("Student id not found - " + id);
        }

        student.setCustomCourse(studentService.getStudentCourses(id));
        return student;
    }

    @PostMapping
    @RequestMapping("/{id}/course/attach")
    public Student attachCourse(@PathVariable Integer id, @RequestBody Map<String, Object> payload) {
        Student student = studentService.findStudentById(id);
        Integer courseId = (Integer) payload.get("course_id");
        if (student == null) {
            throw new CustomNotFoundException("Student id not found - " + id);
        }
        Course course = courseService.findCourseById(courseId);
        if (course == null) {
            throw new CustomNotFoundException("Course id not found - " + courseId);
        }

        studentService.attachCourseToStudent(courseId, student.getId());

        student.setCustomCourse(studentService.getStudentCourses(student.getId()));

        return student;
    }

    @PostMapping
    @RequestMapping("/{id}/course/detach")
    public Student detachCourse(@PathVariable Integer id, @RequestBody Map<String, Object> payload) {
        Student student = studentService.findStudentById(id);
        Integer courseId = (Integer) payload.get("course_id");
        if (student == null) {
            throw new CustomNotFoundException("Student id not found - " + id);
        }
        Course course = courseService.findCourseById(courseId);
        if (course == null) {
            throw new CustomNotFoundException("Course id not found - " + courseId);
        }

        studentService.detachCourseFromStudent(courseId, student.getId());

        student.setCustomCourse(studentService.getStudentCourses(student.getId()));

        return student;
    }

    @PostMapping
    public Student create(@RequestBody final Student student) {
        return studentService.saveStudent(student);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        Student student = studentService.findStudentById(id);
        if (student == null) {
            throw new CustomNotFoundException("Student id not found - " + id);
        }
        studentService.deleteStudent(id);
    }
}
