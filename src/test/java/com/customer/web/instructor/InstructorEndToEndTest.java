package com.customer.web.instructor;

import com.customer.web.entity.web.Instructor;
import com.customer.web.services.InstructorService;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableTransactionManagement
@Transactional("webTransactionManager")
public class InstructorEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InstructorService instructorService;

    public String BASE_URL = "/api/v1/instructor";

    @Test
    public void listInstructorsWithCourses() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/?hasCourses=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void listInstructorsWithoutCourses() throws Exception {

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

        String name = "ve";
        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/name/{firstName}", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Veselin")))
                .andExpect(jsonPath("$.lastName", is("Doinov")))
                .andExpect(jsonPath("$.email", is("veselin_doynov@abv.bg")))
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
        String name = "ve";
        Instructor instructor = instructorService.getByName(name);
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
        String name = "ve";
        Instructor instructor = instructorService.getByName(name);
        String firstNameUpdated = "Updated first name";
        String postData = "{\"firstName\":\"" + firstNameUpdated + "\"}";


        this.mockMvc.perform(MockMvcRequestBuilders.patch(BASE_URL + "/{id}", 69696)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postData))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateInstructorBadRequest() throws Exception {
        String name = "ve";
        Instructor instructor = instructorService.getByName(name);
        String firstNameUpdated = "Updated first name";
        String postData = "{\"firstName\":\"" + firstNameUpdated + "\"}";


        this.mockMvc.perform(MockMvcRequestBuilders.patch(BASE_URL + "/{id}", "not an integer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postData))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteInstructor() throws Exception {
        String name = "ve";
        Instructor instructor = instructorService.getByName(name);
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
