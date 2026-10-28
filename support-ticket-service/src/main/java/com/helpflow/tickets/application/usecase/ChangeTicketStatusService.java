package com.helpflow.tickets.application.usecase;

import com.helpflow.tickets.application.port.input.ChangeTicketStatusUseCase;
import com.helpflow.tickets.application.port.output.TicketRepositoryPort;
import com.helpflow.tickets.application.port.output.UnitOfWorkPort;
import com.helpflow.tickets.domain.exception.InvalidTicketTransitionException;
import com.helpflow.tickets.domain.model.*;
import com.helpflow.tickets.domain.service.TicketTransitionPolicy;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ChangeTicketStatusService implements ChangeTicketStatusUseCase {
    private final TicketRepositoryPort tickets;
    private final UnitOfWorkPort unitOfWork;

    public ChangeTicketStatusService(TicketRepositoryPort tickets, UnitOfWorkPort unitOfWork) {
        this.tickets = tickets;
        this.unitOfWork = unitOfWork;
    }

    @Override
    public Ticket change(UUID ticketId, UUID actorUserId, TicketStatus newStatus, String observation) {
        Ticket old = tickets.findById(ticketId).orElseThrow(() -> new java.util.NoSuchElementException("Ticket no encontrado"));
        if (!TicketTransitionPolicy.canTransition(old.status(), newStatus)) {
            throw new InvalidTicketTransitionException("Transición no permitida: " + old.status() + " -> " + newStatus);
        }
        if (observation == null || observation.isBlank())
            throw new IllegalArgumentException("La observación es obligatoria");
        Ticket updated = new Ticket(old.id(), old.userId(), old.assignedTechnicianId(), old.category(), old.priority(), newStatus,
                old.title(), old.description(), old.createdAt(), old.slaDeadline());
        TicketHistory history = new TicketHistory(ticketId, actorUserId, old.status().name(), newStatus.name(), observation.trim(), Instant.now());
        unitOfWork.updateTicketWithHistory(updated, history);
        return updated;
    }
}
