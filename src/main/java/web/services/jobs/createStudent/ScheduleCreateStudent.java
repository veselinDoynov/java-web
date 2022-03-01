package web.services.jobs.createStudent;

import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduleCreateStudent {

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private CreateStudentRun createStudentRun;

    public void run() {
        int jobId = jobScheduler.hashCode();
        jobScheduler.schedule(LocalDateTime.now().plusMinutes(1), () -> createStudentRun.execute("Dummy student will be created in a minute", jobId));
    }
}
