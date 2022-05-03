package com.customer.web.entity.web.transformed;

import com.customer.web.entity.web.Course;
import com.customer.web.entity.web.Instructor;

import java.util.List;

public class InstructorTransformed {

    private String name;

    private List<CourseTransformed> courses;

    public InstructorTransformed(Instructor instructor) {
        this.setCourses(instructor.getCoursesTransformed());
        this.setName(instructor.getInstructorDetails());
    }

    public List<CourseTransformed> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseTransformed> courses) {
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
