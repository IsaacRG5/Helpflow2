package com.helpflow.tickets.infrastructure.input.rest;

import com.helpflow.tickets.infrastructure.output.persistence.adapter.TicketPersistenceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SlaMonitoringScheduler {
    private static final Logger log = LoggerFactory.getLogger(SlaMonitoringScheduler.class);
    private final TicketPersistenceAdapter tickets;

    public SlaMonitoringScheduler(TicketPersistenceAdapter tickets) {
        this.tickets = tickets;
    }

    @Scheduled(fixedDelayString = "${app.sla.monitor-delay-ms:60000}")
    public void logOverdueCount() {
        long overdue = tickets.countOverdue();
        if (overdue > 0) log.warn("SLA_MONITOR overdueTickets={}", overdue);
    }
}
