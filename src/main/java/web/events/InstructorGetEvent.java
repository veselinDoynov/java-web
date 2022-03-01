package web.events;

import org.springframework.context.ApplicationEvent;

public class InstructorGetEvent extends ApplicationEvent {
    private String message;

    public InstructorGetEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
