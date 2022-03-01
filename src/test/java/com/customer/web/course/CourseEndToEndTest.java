package com.customer.web.course;

import com.customer.web.WebApplication;
import com.customer.web.config.TestPersistenceConfiguration;
import com.customer.web.entity.web.Course;
import com.customer.web.entity.web.Instructor;
import com.customer.web.services.CourseService;
import com.customer.web.services.InstructorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {
        WebApplication.class,
        TestPersistenceConfiguration.class})
@ActiveProfiles("test")
@Transactional("testTransactionManager")
public class CourseEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseService courseService;

    @Autowired
    private InstructorService instructorService;

    public String BASE_URL = "/api/v1/course";

    @Test
    public void listCourses() throws Exception {

        Course course = courseService.saveCourse(new Course("some custom course"));
        Course courseTwo = courseService.saveCourse(new Course("some custom course 2"));
        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

        ;
    }

    @Test
    public void getCourse() throws Exception {
        Course course = courseService.saveCourse(new Course("some custom course"));
        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", course.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(course.getTitle())));
    }

    @Test
    public void getCourseNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", 12312312)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getCourseBadData() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", "not an integer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void deleteCourse() throws Exception {
        Course course = courseService.saveCourse(new Course("some custom course"));
        this.mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", course.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCourseNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", 6969))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void deleteCourseBadRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", "not an integer"))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void addCourse() throws Exception {

        Course course = new Course("some new course on java");
        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(course)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(course.getTitle())));
    }

    @Test
    public void addCourseExists() throws Exception {

        Course course = this.courseService.saveCourse(new Course("some new course on java"));

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(course)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void attachInstructor() throws Exception {

        Course course = this.courseService.saveCourse(new Course("some new course on java"));
        Instructor instructor = this.instructorService.saveInstructor(new Instructor("Instructor", "Java", "java@spring.com"));

        Map<String, Object> postData = new HashMap<>();
        postData.put("instructor_id", instructor.getId());

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/{id}/instructor/attach", course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postData)))
                .andExpect(status().isOk());

        instructor = instructorService.findByInstructorId(instructor.getId());
        course = courseService.findCourseById(course.getId());

        Assertions.assertEquals(instructor, course.getInstructor());
    }

    @Test
    public void attachInstructorCourseNotFound() throws Exception {

        Instructor instructor = this.instructorService.saveInstructor(new Instructor("Instructor", "Java", "java@spring.com"));

        Map<String, Object> postData = new HashMap<>();
        postData.put("instructor_id", instructor.getId());

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/{id}/instructor/attach", 12312323)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postData)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void attachInstructorNotFound() throws Exception {

        Course course = this.courseService.saveCourse(new Course("some new course on java"));

        Map<String, Object> postData = new HashMap<>();
        postData.put("instructor_id", 1123123213);

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/{id}/instructor/attach", course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postData)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void detachInstructor() throws Exception {

        Course course = this.courseService.saveCourse(new Course("some new course on java"));
        Instructor instructor = this.instructorService.saveInstructor(new Instructor("Instructor", "Java", "java@spring.com"));

        Map<String, Object> postData = new HashMap<>();
        postData.put("instructor_id", instructor.getId());

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/{id}/instructor/detach", course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postData)))
                .andExpect(status().isOk());
    }

    @Test
    public void detachInstructorCourseNotFound() throws Exception {

        Instructor instructor = this.instructorService.saveInstructor(new Instructor("Instructor", "Java", "java@spring.com"));

        Map<String, Object> postData = new HashMap<>();
        postData.put("instructor_id", instructor.getId());

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/{id}/instructor/detach", 12312323)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postData)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void detachInstructorNotFound() throws Exception {

        Course course = this.courseService.saveCourse(new Course("some new course on java"));

        Map<String, Object> postData = new HashMap<>();
        postData.put("instructor_id", 1123123213);

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/{id}/instructor/detach", course.getId())
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
