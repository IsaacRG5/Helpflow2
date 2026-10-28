package com.helpflow.tickets.infrastructure.input.rest.dto;

import com.helpflow.tickets.domain.model.Ticket;

import java.time.Instant;
import java.util.UUID;

public record TicketResponse(UUID id, UUID userId, UUID assignedTechnicianId, String category, String priority,
                             String status, String title, String description, Instant createdAt, Instant slaDeadline) {
    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(ticket.id(), ticket.userId(), ticket.assignedTechnicianId(), ticket.category().name(), ticket.priority().name(),
                ticket.status().name(), ticket.title(), ticket.description(), ticket.createdAt(), ticket.slaDeadline());
    }
}
