package com.customer.web;

import com.customer.web.controllers.InstructorController;
import com.customer.web.entity.Instructor;
import com.customer.web.services.InstructorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(InstructorController.class)
public class InstructorMockTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InstructorService instructorService;

    public String BASE_URL = "/api/v1/instructor";

    @Test
    public void listInstructorsMock() throws Exception {

        Instructor test = new Instructor("test", "testov", "test@testov.com");
        List<Instructor> instructors = Arrays.asList(test);
        given(this.instructorService.getOrderedInstructors()).willReturn(instructors);

        this.mvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(test.getFirstName())));
    }
}
