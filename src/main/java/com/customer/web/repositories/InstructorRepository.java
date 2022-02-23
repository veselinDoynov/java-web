package com.customer.web.repositories;

import com.customer.web.entity.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

    @Query(value = "SELECT * FROM instructor Order by first_name", nativeQuery = true)
    Collection<Instructor> getOrderedInstructors();

    @Query(value = "SELECT DISTINCT instructor.* FROM instructor \n" +
            "            Join course on course.instructor_id = instructor.id\n" +
            "            Order by instructor.first_name" , nativeQuery = true)
    Collection<Instructor> getOrderedInstructorsHavingCourse();

    @Query(value = "SELECT DISTINCT instructor.* FROM instructor \n" +
            "            Left Join course on course.instructor_id = instructor.id\n" +
            "            Where course.instructor_id is null\n" +
            "            Order by instructor.first_name" , nativeQuery = true)
    Collection<Instructor> getOrderedInstructorsNotHavingCourse();

    @Query(
            value = "SELECT * FROM instructor Order by first_name",
            countQuery = "SELECT count(*) FROM instructor",
            nativeQuery = true)
    Page<Instructor> getOrderedInstructorsPaginated(Pageable pageable);


    @Query(value = "SELECT * FROM instructor Where first_name LIKE %:firstName% Order by id limit 1",
            nativeQuery = true)
    Instructor getInstructorByFirstName(@Param("firstName") String firstName);
}
