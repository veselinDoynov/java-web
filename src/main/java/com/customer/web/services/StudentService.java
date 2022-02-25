package com.customer.web.services;

import com.customer.web.entity.web.Student;
import com.customer.web.repositories.web.CourseRepository;
import com.customer.web.repositories.web.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Collection<Student> list() {

        return studentRepository.findAll();
    }

    public Student findStudentById(Integer id) {

        Optional<Student> result = studentRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }

        return null;
    }

    public List<Object> getStudentCourses(Integer id) {

        return courseRepository.getStudentCourses(id);
    }

    public void attachCourseToStudent(Integer courseId, Integer studentId) {
        studentRepository.attachCourseToStudent(courseId, studentId);
    }

    public void detachCourseFromStudent(Integer courseId, Integer studentId) {
        studentRepository.detachCourseFromStudent(courseId, studentId);
    }

    public Student saveStudent(Student student) {
        return studentRepository.saveAndFlush(student);
    }

    public String deleteStudent(Integer id) {
        studentRepository.deleteById(id);
        return "Success";
    }
}
