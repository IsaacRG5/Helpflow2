package com.helpflow.tickets.domain;

import com.helpflow.tickets.domain.model.TicketPriority;
import com.helpflow.tickets.domain.service.SlaPolicy;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlaPolicyTest {
    @Test
    void criticalSlaIsTwoHours() {
        Instant start = Instant.parse("2026-01-01T00:00:00Z");
        assertEquals(start.plusSeconds(7200), SlaPolicy.deadlineFor(TicketPriority.CRITICA, start));
    }
}
