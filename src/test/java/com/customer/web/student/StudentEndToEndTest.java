package com.customer.web.student;

import com.customer.web.entity.web.Course;
import com.customer.web.entity.web.Student;
import com.customer.web.services.CourseService;
import com.customer.web.services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class StudentEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    public String BASE_URL = "/api/v1/student";

    @Test
    public void listStudents() throws Exception {

        Student student =  new Student("Student", "Javarov", "stu@dent.com");

        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudent() throws Exception {
        Student student =  new Student("Student", "Javarov", "stu@dent.com");
        studentService.saveStudent(student);
        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", student.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(student.getEmail())));
    }

    @Test
    public void getStudentNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}",12312312)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getStudentBadData() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}","not an integer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void deleteStudent() throws Exception {
        Student student =  new Student("Student", "Javarov", "stu@dent.com");
        studentService.saveStudent(student);
        this.mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", student.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteStudentNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", 6969))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void deleteStudentBadRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", "not an integer"))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void addStudent() throws Exception {

        Student student =  new Student("Student", "Javarov", "stu@dent.com");
        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(student.getEmail())));
    }

    @Test
    public void attachCourseToStudent() throws Exception {

        Course course = this.courseService.saveCourse(new Course("some new course on java"));
        Student student = this.studentService.saveStudent(new Student("Student", "Javarov", "stu@dent.com"));

        Map<String, Object> postData = new HashMap<>();
        postData.put("course_id", course.getId());

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/{id}/course/attach", student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postData)))
                .andExpect(status().isOk());
    }

    @Test
    public void attachStudentNotFound() throws Exception {

        Course course = this.courseService.saveCourse(new Course("some new course on java"));

        Map<String, Object> postData = new HashMap<>();
        postData.put("course_id", course.getId());

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/{id}/course/attach", 12312323)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postData)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void attachCourseNotFound() throws Exception {

        Student student = this.studentService.saveStudent(new Student("Student", "Javarov", "stu@dent.com"));

        Map<String, Object> postData = new HashMap<>();
        postData.put("course_id", 1123123213);

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/{id}/course/attach", student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postData)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void detachCourse() throws Exception {

        Course course = this.courseService.saveCourse(new Course("some new course on java"));
        Student student = this.studentService.saveStudent(new Student("Student", "Javarov", "stu@dent.com"));

        Map<String, Object> postData = new HashMap<>();
        postData.put("course_id", course.getId());

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/{id}/course/detach", student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postData)))
                .andExpect(status().isOk());
    }

    @Test
    public void detachCourseNotFound() throws Exception {

        Student student = this.studentService.saveStudent(new Student("Student", "Javarov", "stu@dent.com"));


        Map<String, Object> postData = new HashMap<>();
        postData.put("course_id", 12312312);

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/{id}/course/detach", student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postData)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void detachCourseStudentNotFound() throws Exception {

        Course course = this.courseService.saveCourse(new Course("some new course on java"));

        Map<String, Object> postData = new HashMap<>();
        postData.put("course_id", course.getId());

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/{id}/course/detach", 1323233)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postData)))
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
