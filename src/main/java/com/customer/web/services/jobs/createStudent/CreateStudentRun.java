package com.customer.web.services.jobs.createStudent;

import com.customer.web.entity.web.Student;
import com.customer.web.services.StudentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateStudentRun {

    private static Log log = LogFactory.getLog(CreateStudentRun.class);

    @Autowired
    private StudentService studentService;

    @Job(name = "Create a dummy student", retries = 3)
    public void execute(String message, int jobId) {
        System.out.println("Job: " + message);

        Student student = new Student("Job - " + jobId, "MadeMe", "job@made.me");
        student = studentService.saveStudent(student);

        System.out.println("Student created from this job : " + student.toString());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Job:" + e.getMessage());
        } finally {
            System.out.println("Job: finished");
        }
    }

}
