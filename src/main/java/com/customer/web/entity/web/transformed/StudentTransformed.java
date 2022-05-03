package com.customer.web.entity.web.transformed;

import com.customer.web.entity.web.Student;

public class StudentTransformed {

    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public StudentTransformed (Student student) {
        this.setFullName(student.getFirstName() + " " + student.getLastName());
        this.setEmail(student.getEmail());
    }


}
