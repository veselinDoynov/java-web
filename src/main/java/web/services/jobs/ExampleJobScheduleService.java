package web.services.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Service;

@Service
public class ExampleJobScheduleService {

    private static Log log = LogFactory.getLog(ExampleJobScheduleService.class);

    @Job(name = "The sample job without variable")
    public void execute() {
        execute("Hello world! from job queue");
    }

    @Job(name = "The sample job with variable %0")
    public void execute(String message) {
        System.out.println("Job:" + message);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Job:" + e.getMessage());
        } finally {
            System.out.println("Job: finished");
        }
    }

}
