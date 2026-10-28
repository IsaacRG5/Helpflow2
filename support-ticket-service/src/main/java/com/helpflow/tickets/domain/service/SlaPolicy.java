package com.helpflow.tickets.domain.service;

import com.helpflow.tickets.domain.model.TicketPriority;

import java.time.Duration;
import java.time.Instant;

public final class SlaPolicy {
    private SlaPolicy() {
    }

    public static Instant deadlineFor(TicketPriority priority, Instant createdAt) {
        Duration duration = switch (priority) {
            case CRITICA -> Duration.ofHours(2);
            case ALTA -> Duration.ofHours(8);
            case MEDIA -> Duration.ofHours(24);
            case BAJA -> Duration.ofHours(72);
        };
        return createdAt.plus(duration);
    }
}
