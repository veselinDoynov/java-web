package com.customer.web.entity.web;

import java.util.List;

public class InstructorTransformed {

    private String name;

    private List<Course> courses;

    public InstructorTransformed(Instructor instructor) {
        this.setCourses(instructor.getCourses());
        this.setName(instructor.getInstructorDetails());
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
