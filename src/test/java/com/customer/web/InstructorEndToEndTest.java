package com.customer.web;

import com.customer.web.entity.Instructor;
import com.customer.web.services.InstructorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebApplication.class})
@Transactional
@AutoConfigureMockMvc //need this in Spring Boot test
public class InstructorEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InstructorService instructorService;

    public String BASE_URL = "/api/v1/instructor";

    @Test
    public void listInstructors() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

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
    public void deleteInstructor() throws Exception {
        String name = "ve";
        Instructor instructor = instructorService.getByName(name);
        this.mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", instructor.getId()))
                .andExpect(status().isOk())
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
