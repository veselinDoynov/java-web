package com.customer.web.publishers;

import com.customer.web.events.InstructorGetEvent;
import com.customer.web.events.InstructorListEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class InstructorPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishInstructorGet(final String message) {
        InstructorGetEvent instructorEvent = new InstructorGetEvent(this, message);
        applicationEventPublisher.publishEvent(instructorEvent);
    }

    public void publishInstructorList(final String message) {
        InstructorListEvent instructorEvent = new InstructorListEvent(this, message);
        applicationEventPublisher.publishEvent(instructorEvent);
    }
}
