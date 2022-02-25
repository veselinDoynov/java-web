package com.customer.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "org.jobrunr.background-job-server.enabled=false",
        "org.jobrunr.dashboard.enabled=false",
})
class WebApplicationTests {

    @Test
    void contextLoads() {
    }

}
