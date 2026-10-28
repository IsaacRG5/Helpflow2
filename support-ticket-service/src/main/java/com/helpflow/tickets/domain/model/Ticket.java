package com.helpflow.tickets.domain.model;

import java.time.Instant;
import java.util.UUID;

public record Ticket(UUID id, UUID userId, UUID assignedTechnicianId, TicketCategory category,
                     TicketPriority priority, TicketStatus status, String title, String description,
                     Instant createdAt, Instant slaDeadline) {
}
