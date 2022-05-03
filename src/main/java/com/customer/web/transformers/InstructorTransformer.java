package com.customer.web.transformers;

import com.customer.web.entity.web.Instructor;
import com.customer.web.entity.web.transformed.InstructorTransformed;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class InstructorTransformer {

    public Collection<InstructorTransformed> getOrdered(Collection<Instructor> instructors) {

        return instructors.stream()
                .sorted(Comparator.comparing(Instructor::getFirstName))
                .map(instructor -> {
                    return new InstructorTransformed(instructor);
                })
                .collect(Collectors.toList());
    }
}
