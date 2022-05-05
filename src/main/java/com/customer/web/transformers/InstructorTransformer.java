package com.customer.web.transformers;

import com.customer.web.entity.web.Course;
import com.customer.web.entity.web.Instructor;
import com.customer.web.entity.web.transformed.InstructorTransformed;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class InstructorTransformer {

    public Collection<InstructorTransformed> transform(Collection<Instructor> instructors, Boolean hasCourses) {

        Stream <Instructor> instructorStream = instructors.stream();

        return addTransformation(
                addSorting(
                        addFilter(hasCourses, instructorStream)
                )
        ).collect(Collectors.toList());
    }

    public Stream <Instructor> addFilter(Boolean hasCourses, Stream <Instructor> instructorStream) {

        if (hasCourses != null) {
            Predicate <Instructor> filter = instructor ->  {
                List <Course> courseList = Optional.of(instructor.getCourses()).orElse(Collections.emptyList());
                return hasCourses.equals(Boolean.TRUE) ?  !courseList.isEmpty() : courseList.isEmpty();
            };
            return instructorStream.filter(filter);
        }

        return instructorStream;
    }

    public Stream <Instructor> addSorting(Stream <Instructor> instructorStream) {

        return instructorStream
                .sorted(Comparator.comparing(instructor -> Optional.of(instructor.getCourses()).orElse(Collections.emptyList()).size()));
    }

    public Stream <InstructorTransformed> addTransformation(Stream <Instructor> instructorStream) {

        return instructorStream.map(InstructorTransformed::new);
    }
}
