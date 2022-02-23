package com.customer.web.listeners;

import com.customer.web.entity.Instructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;

public class AuditTrailListener {
    private static Log log = LogFactory.getLog(AuditTrailListener.class);

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(Instructor instructor) {
        if (instructor.getId() == 0) {
            log.info("[Instructor AUDIT] About to add a Instructor");
        } else {
            log.info("[Instructor AUDIT] About to update/delete Instructor: " + instructor.getId());
        }
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(Instructor instructor) {
        log.info("[Instructor AUDIT] add/update/delete complete for Instructor: " + instructor.getId());
    }

    @PostLoad
    private void afterLoad(Instructor instructor) {
        log.info("[Instructor AUDIT] Instructor loaded from database: " + instructor.getId());
    }
}
