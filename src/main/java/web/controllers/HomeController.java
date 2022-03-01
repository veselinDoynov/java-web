package web.controllers;

import web.services.VersionService;
import web.services.jobs.ExampleJobScheduleService;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HomeController {

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private ExampleJobScheduleService exampleJobService;

    @Autowired
    private VersionService versionService;

    @GetMapping
    @RequestMapping("/version")
    public String getVersion() {

        jobScheduler.enqueue(() -> exampleJobService.execute("Instant job"));

        return versionService.getVersion();
    }
}
