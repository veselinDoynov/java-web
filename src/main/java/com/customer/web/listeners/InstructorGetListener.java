package com.customer.web.listeners;

import com.customer.web.events.InstructorGetEvent;
import com.customer.web.services.jobs.ExampleJobRunnerService;
import com.customer.web.services.jobs.createStudent.ScheduleCreateStudent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class InstructorGetListener implements ApplicationListener<InstructorGetEvent> {

    private static Log log = LogFactory.getLog(InstructorGetListener.class);

    @Autowired
    private ExampleJobRunnerService jobRunnerService;

    @Autowired
    private ScheduleCreateStudent studentSchedule;

    @Override
    public void onApplicationEvent(InstructorGetEvent event) {
        log.info("[Instructor get] : " + event.getMessage());
        jobRunnerService.runInstantJob();
        jobRunnerService.runWithDelayJob();
        studentSchedule.run();
    }
}
