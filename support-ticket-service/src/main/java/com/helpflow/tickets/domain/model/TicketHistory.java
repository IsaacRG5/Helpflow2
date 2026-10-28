package com.helpflow.tickets.domain.model;

import java.time.Instant;
import java.util.UUID;

public record TicketHistory(UUID ticketId, UUID changedByUserId, String previousStatus,
                            String newStatus, String observation, Instant changedAt) {
}
