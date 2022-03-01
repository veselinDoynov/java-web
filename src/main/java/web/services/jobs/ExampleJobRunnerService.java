package web.services.jobs;

import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExampleJobRunnerService {

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private ExampleJobScheduleService jobScheduleService;

    public void runInstantJob() {
        jobScheduler.enqueue(() -> jobScheduleService.execute("Instant job"));

    }

    public void runWithDelayJob() {
        jobScheduler.schedule(LocalDateTime.now().plusMinutes(5), () -> jobScheduleService.execute("Job will execute in 5 min"));
    }

    public void runScheduledJob() {
        jobScheduler.scheduleRecurrently(
                Cron.every15minutes(),
                () -> jobScheduleService.execute("Job run every 15 minutes"));
    }
}
