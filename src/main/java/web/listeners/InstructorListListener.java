package web.listeners;

import web.events.InstructorListEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class InstructorListListener implements ApplicationListener<InstructorListEvent> {

    private static Log log = LogFactory.getLog(InstructorListListener.class);

    @Override
    public void onApplicationEvent(InstructorListEvent event) {
        System.out.println("Instructor list - " + event.getMessage());
        log.info("[Instructor list] : " + event.getMessage());
    }
}