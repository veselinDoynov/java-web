package com.customer.web.entity.web.transformed;

import com.customer.web.entity.web.Course;
import com.customer.web.entity.web.Student;

import java.util.List;

public class CourseTransformed {

    private String name;

    private List<StudentTransformed> students;

    public CourseTransformed(Course course) {
        this.setName(course.getTitle());
        this.setStudents(course.getStudentsTransformed());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StudentTransformed> getStudents() {
        return students;
    }

    public void setStudents(List<StudentTransformed> students) {
        this.students = students;
    }
}
