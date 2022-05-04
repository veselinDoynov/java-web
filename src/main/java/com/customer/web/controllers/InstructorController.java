package com.customer.web.controllers;

import com.customer.web.controllers.exception.CustomNotFoundException;
import com.customer.web.entity.web.Instructor;
import com.customer.web.entity.web.transformed.InstructorTransformed;
import com.customer.web.services.InstructorService;
import com.customer.web.transformers.InstructorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private InstructorTransformer instructorTransformer;

    @GetMapping
    public Collection<Instructor> list(@RequestParam(required = false) Boolean hasCourses) {
        if (hasCourses != null) {
            return instructorService.getOrderedInstructorsFilteredByCoursePresents(hasCourses);
        }
        return instructorService.getOrderedInstructors();
    }

    @GetMapping
    @RequestMapping("/transformed")
    public Collection<InstructorTransformed> listTransformed(@RequestParam(required = false) Boolean hasCourses) {

        Collection<Instructor> instructors = instructorService.getOrderedInstructors();

        instructors.stream().forEach(instructor -> {
            System.out.println(instructor.getCourses());
        });

        if (hasCourses != null) {
            return instructorTransformer.transform(instructors, hasCourses ? "Yes" : "No");
        }
        return instructorTransformer.transform(instructors, null);
    }

    @GetMapping
    @RequestMapping("/paging")
    public Page<Instructor> listPaginated(Pageable pageable) {

        return instructorService.getInstructorsPaginated(pageable);
    }

    @GetMapping
    @RequestMapping("/name/{firstName}")
    public Instructor getByName(@PathVariable String firstName) {

        Instructor instructor = instructorService.getByName(firstName);
        if (instructor == null) {
            throw new CustomNotFoundException("Instructor with name:  " + firstName);
        }

        return instructor;
    }

    @GetMapping
    @RequestMapping("{id}")
    public Object get(@PathVariable Integer id) {
        Instructor instructor = instructorService.findByInstructorId(id);
        if (instructor == null) {
            throw new CustomNotFoundException("Instructor id not found - " + id);
        }

        return instructor;
    }

    @PostMapping
    public Instructor create(@RequestBody final Instructor instructor) {
        return instructorService.saveInstructor(instructor);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PATCH)
    public Object update(@PathVariable Integer id, @RequestBody Instructor instructor) {

        Instructor updateInstructor = instructorService.updateInstructor(id, instructor);

        if (updateInstructor == null) {
            throw new CustomNotFoundException("Instructor id not found - " + id);
        }

        return updateInstructor;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        Instructor instructor = instructorService.findByInstructorId(id);
        if (instructor == null) {
            throw new CustomNotFoundException("Instructor id not found - " + id);
        }
        instructorService.deleteInstructor(id);
    }
}
