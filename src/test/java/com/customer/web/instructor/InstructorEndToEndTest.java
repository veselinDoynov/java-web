package com.customer.web.instructor;

import com.customer.web.WebApplication;
import com.customer.web.config.TestPersistenceConfiguration;
import com.customer.web.entity.web.Course;
import com.customer.web.entity.web.Instructor;
import com.customer.web.services.CourseService;
import com.customer.web.services.InstructorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
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
public class InstructorEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private CourseService courseService;

    public String BASE_URL = "/api/v1/instructor";

    @Test
    public void listInstructorsWithCourses() throws Exception {

        Instructor instructor = new Instructor("zns", "struc", "tor@dot.com");
        Course course = new Course("some new test course in list instructor");

        instructor = instructorService.saveInstructor(instructor);
        course.setInstructor(instructor);
        courseService.saveCourse(course);


        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/?hasCourses=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(instructor.getId()))
                .andExpect(jsonPath("$[0].email").value(instructor.getEmail()))
                .andExpect(jsonPath("$[0].firstName").value(instructor.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(instructor.getLastName()))
        ;
    }

    @Test
    public void listInstructorsWithoutCourses() throws Exception {
        Instructor instructor = new Instructor("ins", "struc", "tor@dot.com");
        instructor.setCourses(null);
        instructor = instructorService.saveInstructor(instructor);

        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/?hasCourses=0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void listInstructorsAll() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getInstructorByName() throws Exception {

        Instructor instructor = new Instructor("instructor", "Dodo", "inst@tor.com");
        instructorService.saveInstructor(instructor);
        String name = "tructor";
        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/name/{firstName}", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(instructor.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(instructor.getLastName())))
                .andExpect(jsonPath("$.email", is(instructor.getEmail())))
        ;
    }

    @Test
    public void getInstructorByNameWrongUrl() throws Exception {

        String name = "ve";
        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/name/v2/{firstName}", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addInstructor() throws Exception {

        Instructor instructor = new Instructor("test", "testov", "test@testov@com");

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(instructor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(instructor.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(instructor.getLastName())))
                .andExpect(jsonPath("$.email", is(instructor.getEmail())));
        ;
    }

    @Test
    public void updateInstructor() throws Exception {
        Instructor instructor = new Instructor("instructor", "Dodo", "inst@tor.com");
        instructorService.saveInstructor(instructor);
        String firstNameUpdated = "Updated first name";
        String postData = "{\"firstName\":\"" + firstNameUpdated + "\"}";


        this.mockMvc.perform(MockMvcRequestBuilders.patch(BASE_URL + "/{id}", instructor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(firstNameUpdated)))
                .andExpect(jsonPath("$.lastName", is(instructor.getLastName())))
                .andExpect(jsonPath("$.email", is(instructor.getEmail())));
        ;
    }

    @Test
    public void updateInstructorNotFound() throws Exception {

        String firstNameUpdated = "Updated first name";
        String postData = "{\"firstName\":\"" + firstNameUpdated + "\"}";


        this.mockMvc.perform(MockMvcRequestBuilders.patch(BASE_URL + "/{id}", 69696)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postData))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateInstructorBadRequest() throws Exception {
        Instructor instructor = new Instructor("instructor", "Dodo", "inst@tor.com");
        instructorService.saveInstructor(instructor);
        String firstNameUpdated = "Updated first name";
        String postData = "{\"firstName\":\"" + firstNameUpdated + "\"}";


        this.mockMvc.perform(MockMvcRequestBuilders.patch(BASE_URL + "/{id}", "not an integer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postData))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteInstructor() throws Exception {
        Instructor instructor = new Instructor("instructor", "Dodo", "inst@tor.com");
        instructorService.saveInstructor(instructor);
        this.mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", instructor.getId()))
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void deleteInstructorNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", 6969))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void deleteInstructorBadRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", "not an integer"))
                .andExpect(status().isBadRequest())
        ;
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
