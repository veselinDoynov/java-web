package com.customer.web.transformers;

import com.customer.web.entity.web.Course;
import com.customer.web.entity.web.Instructor;
import com.customer.web.entity.web.transformed.CourseTransformed;
import com.customer.web.entity.web.transformed.InstructorTransformed;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class InstructorTransformer {

    public Collection<InstructorTransformed> transform(Collection<Instructor> instructors, String hasCourses) {

        Stream<InstructorTransformed> instructorTransformedStream = instructors.stream()
                .sorted(Comparator.comparing(instructor -> {
                    List <Course> courseList = instructor.getCourses();
                    if(courseList == null) {
                        courseList = new ArrayList<>();
                    }
                    return courseList.stream().count();
                }))
                .map(instructor -> {
                    return new InstructorTransformed(instructor);
                });

        if (hasCourses != null) {
            Predicate <InstructorTransformed> filter = instructor -> {
                List <CourseTransformed> courseList = instructor.getCourses();

                if(hasCourses == "Yes") {
                    return courseList != null && !courseList.isEmpty();
                } else {
                    return courseList == null || courseList.isEmpty();
                }
            };

            instructorTransformedStream = instructorTransformedStream.filter(filter);
        }

        return instructorTransformedStream.collect(Collectors.toList());
    }
}
