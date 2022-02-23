package com.customer.web.events;

import org.springframework.context.ApplicationEvent;

public class InstructorListEvent extends ApplicationEvent {
    private String message;

    public InstructorListEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}