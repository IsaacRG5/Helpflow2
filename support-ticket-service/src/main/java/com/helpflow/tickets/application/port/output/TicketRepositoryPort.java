package com.helpflow.tickets.application.port.output;

import com.helpflow.tickets.domain.model.Ticket;
import com.helpflow.tickets.domain.model.TicketStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepositoryPort {
    Optional<Ticket> findById(UUID id);

    List<Ticket> findByUserId(UUID userId);

    List<Ticket> findByTechnicianId(UUID technicianId);

    List<Ticket> findAll();
}
