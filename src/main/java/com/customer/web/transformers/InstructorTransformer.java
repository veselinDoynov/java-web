package com.customer.web.transformers;

import com.customer.web.entity.web.Course;
import com.customer.web.entity.web.Instructor;
import com.customer.web.entity.web.transformed.CourseTransformed;
import com.customer.web.entity.web.transformed.InstructorTransformed;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class InstructorTransformer {

    public Collection<InstructorTransformed> transform(Collection<Instructor> instructors, String hasCourses) {

        Stream <Instructor> instructorStream = instructors.stream();

        Stream <String> stringStream = Stream.of("asd", "bdffd", "asa");

        stringStream.sorted( (s1, s2) -> s1.compareTo(s2)).forEach(System.out::println);

        stringStream.sorted(Comparator.comparingInt(s -> s.charAt(0))).forEach(System.out::println);

        Stream <Instructor> instructorStream2 = instructors.stream();

        Map<String, List<Instructor>> result =  instructorStream2
                .filter(instructor -> instructor.getCourses() != null && !instructor.getCourses().isEmpty())
                .sorted(Comparator.comparing((Instructor instructor) -> {
                    List <Course> courseList = instructor.getCourses();
                    courseList = courseList == null ? new ArrayList<>() : courseList;

                    return courseList.stream().count();
                }).reversed())
                .collect(Collectors.groupingBy(instructor -> {
                    return String.join("," ,instructor.getCourses().stream().map(Course::getTitle).collect(Collectors.toList()));

                }));
        System.out.println("Grouped");
        System.out.println(result);

        return addTransformation(
                addSorting(
                        addFilter(hasCourses, instructorStream)
                )
        ).collect(Collectors.toList());
    }

    public Stream <Instructor> addFilter(String hasCourses, Stream <Instructor> instructorStream) {

        if (hasCourses != null) {
            Predicate <Instructor> filter = instructor -> {
                List <Course> courseList = instructor.getCourses();

                if(hasCourses == "Yes") {
                    return courseList != null && !courseList.isEmpty();
                } else {
                    return courseList == null || courseList.isEmpty();
                }
            };
            return instructorStream.filter(filter);
        }

        return instructorStream;
    }

    public Stream <Instructor> addSorting(Stream <Instructor> instructorStream) {

        return instructorStream
                .sorted(Comparator.comparing(instructor -> {
                    List <Course> courseList = instructor.getCourses();
                    courseList = courseList == null ? new ArrayList<>() : courseList;

                    return courseList.stream().count();
                }));
    }

    public Stream <InstructorTransformed> addTransformation(Stream <Instructor> instructorStream) {

        return instructorStream.map(instructor -> {
            return new InstructorTransformed(instructor);
        });
    }
}
