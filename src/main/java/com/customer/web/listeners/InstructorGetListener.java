package com.customer.web.listeners;

import com.customer.web.events.InstructorGetEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class InstructorGetListener implements ApplicationListener<InstructorGetEvent> {

    private static Log log = LogFactory.getLog(InstructorGetListener.class);

    @Override
    public void onApplicationEvent(InstructorGetEvent event) {
        log.info("[Instructor get] : " + event.getMessage());
    }
}
