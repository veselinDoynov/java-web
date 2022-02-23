package com.customer.web.services;

import com.customer.web.entity.Instructor;
import com.customer.web.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    public Collection<Instructor> getOrderedInstructors() {
        return instructorRepository.getOrderedInstructors();
    }

    public Page<Instructor> getInstructorsPaginated(Pageable pageable) {

        return instructorRepository.getOrderedInstructorsPaginated(pageable);
    }

    public Instructor getByName(String firstName) {
        return instructorRepository.getInstructorByFirstName(firstName);
    }

    public Instructor findByInstructorId(Integer id) {

        Optional<Instructor> result = instructorRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }

        return null;
    }

    public Instructor saveInstructor(Instructor instructor) {
        return instructorRepository.saveAndFlush(instructor);
    }

    public Instructor updateInstructor(Integer id, Instructor instructor) {

        Instructor existingInstructor = this.findByInstructorId(id);

        if (existingInstructor == null) {
            return null;
        }

        if (instructor.getFirstName() != null) {
            existingInstructor.setFirstName(instructor.getFirstName());
        }

        if (instructor.getLastName() != null) {
            existingInstructor.setLastName(instructor.getLastName());
        }

        return instructorRepository.saveAndFlush(existingInstructor);
    }

    public String deleteInstructor(Integer id) {
        instructorRepository.deleteById(id);
        return "Success";
    }
}
