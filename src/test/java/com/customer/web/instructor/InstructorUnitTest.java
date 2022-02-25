package com.customer.web.instructor;

import com.customer.web.entity.web.Instructor;
import com.customer.web.repositories.web.InstructorRepository;
import com.customer.web.services.InstructorService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InstructorUnitTest {

    @Autowired
    private InstructorService instructorService;

    @MockBean
    private InstructorRepository instructorRepository;

    @Test
    public void listInstructorsMock() throws Exception {

        List<Instructor> instructors = new ArrayList<>();
        instructors.add(new Instructor("test", "testov", "test@testov.com"));
        instructors.add(new Instructor("test1", "testov1", "test1@testov.com"));
        given(this.instructorRepository.getOrderedInstructors()).willReturn(instructors);

        Collection<Instructor> result = this.instructorService.getOrderedInstructors();
        Assertions.assertTrue(result.containsAll(instructors));
        Assertions.assertEquals(instructors.toArray()[0], result.toArray()[0]);
        Assertions.assertEquals(instructors.toArray()[1], result.toArray()[1]);
    }

    @Test
    public void getInstructor() throws Exception {

        Instructor instructor = new Instructor("test", "testov", "test@testov.com");
        doReturn(Optional.of(instructor)).when(this.instructorRepository).findById(instructor.getId());

        Instructor result = this.instructorService.findByInstructorId(instructor.getId());
        Assertions.assertEquals(instructor.getLastName(), result.getLastName());
    }

    @Test
    public void getInstructorNotFound() throws Exception {

        Instructor instructor = new Instructor("test", "testov", "test@testov.com");
        doReturn(Optional.empty()).when(this.instructorRepository).findById(instructor.getId());

        Instructor result = this.instructorService.findByInstructorId(instructor.getId());
        Assertions.assertNull(result);
    }

    @Test
    public void getInstructorByName() throws Exception {

        Instructor instructor = new Instructor("test", "testov", "test@testov.com");
        given(this.instructorRepository.getInstructorByFirstName(instructor.getFirstName())).willReturn(instructor);

        Instructor result = this.instructorService.getByName(instructor.getFirstName());
        Assertions.assertEquals(instructor.getLastName(), result.getLastName());
    }

    @Test
    public void getInstructorByNameNotFound() throws Exception {

        Instructor instructor = new Instructor("test", "testov", "test@testov.com");
        given(this.instructorRepository.getInstructorByFirstName(instructor.getFirstName())).willReturn(null);

        Instructor result = this.instructorService.getByName(instructor.getFirstName());
        Assertions.assertNull(result);
    }

    @Test
    public void createInstructor() throws Exception {
        Instructor instructor = new Instructor("test", "testov", "test@testov.com");
        given(this.instructorRepository.saveAndFlush(instructor)).willReturn(instructor);

        Instructor result = this.instructorService.saveInstructor(instructor);
        Assertions.assertEquals(instructor, result);
    }

    @Test
    public void updateInstructor() throws Exception {
        Instructor instructor = new Instructor("test", "testov", "test@testov.com");
        Instructor instructorUpdateData = new Instructor("test1", "testov1", "test@testov.com");

        doReturn(Optional.of(instructor)).when(this.instructorRepository).findById(instructor.getId());
        doReturn(instructorUpdateData).when(this.instructorRepository).saveAndFlush(any(Instructor.class));

        Instructor result = this.instructorService.updateInstructor(instructor.getId(), instructorUpdateData);
        Assertions.assertEquals(instructorUpdateData.getFirstName(), result.getFirstName());
        Assertions.assertEquals(instructorUpdateData.getLastName(), result.getLastName());
    }

    @Test
    public void updateInstructorNotFound() throws Exception {
        Instructor instructor = new Instructor("test", "testov", "test@testov.com");
        Instructor instructorUpdateData = new Instructor("test1", "testov1", "test@testov.com");

        doReturn(Optional.empty()).when(this.instructorRepository).findById(instructor.getId());

        Instructor result = this.instructorService.updateInstructor(instructor.getId(), instructorUpdateData);
        Assertions.assertNull(result);
    }

    public void deleteInstructor() throws Exception {

        Instructor instructor = new Instructor("test", "testov", "test@testov.com");
        doReturn(Optional.of(instructor)).when(this.instructorRepository).deleteById(instructor.getId());
        String result = this.instructorService.deleteInstructor(instructor.getId());
        Assertions.assertEquals("Success", result);
    }
}
