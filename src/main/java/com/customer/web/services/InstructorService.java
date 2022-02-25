package com.customer.web.services;

import com.customer.web.entity.web.Instructor;
import com.customer.web.publishers.InstructorPublisher;
import com.customer.web.repositories.web.InstructorRepository;
import com.customer.web.services.jobs.ExampleJobScheduleService;
import org.jobrunr.scheduling.JobScheduler;
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

    @Autowired
    private InstructorPublisher instructorPublisher;

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private ExampleJobScheduleService exampleJobService;

    public Collection<Instructor> getOrderedInstructors() {

        Collection<Instructor> instructors = instructorRepository.getOrderedInstructors();
        instructorPublisher.publishInstructorList(instructors.toString());

        return instructors;
    }

    public Collection<Instructor> getOrderedInstructorsFilteredByCoursePresents(boolean havingCourse) {
        if (havingCourse) {
            return instructorRepository.getOrderedInstructorsHavingCourse();
        }
        return instructorRepository.getOrderedInstructorsNotHavingCourse();
    }

    public Page<Instructor> getInstructorsPaginated(Pageable pageable) {

        return instructorRepository.getOrderedInstructorsPaginated(pageable);
    }

    public Instructor getByName(String firstName) {
        Instructor instructor = instructorRepository.getInstructorByFirstName(firstName);

        instructorPublisher.publishInstructorGet(instructor != null ? instructor.toString(): null);
        return instructor;
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
