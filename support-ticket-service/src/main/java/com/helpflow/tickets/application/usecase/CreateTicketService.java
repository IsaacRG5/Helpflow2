package com.helpflow.tickets.application.usecase;

import com.helpflow.tickets.application.port.input.CreateTicketUseCase;
import com.helpflow.tickets.application.port.output.DispatchServicePort;
import com.helpflow.tickets.application.port.output.TicketRepositoryPort;
import com.helpflow.tickets.application.port.output.UnitOfWorkPort;
import com.helpflow.tickets.application.port.output.UserRepositoryPort;
import com.helpflow.tickets.domain.model.*;
import com.helpflow.tickets.domain.service.SlaPolicy;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class CreateTicketService implements CreateTicketUseCase {
    private final UserRepositoryPort users;
    private final DispatchServicePort dispatch;
    private final UnitOfWorkPort unitOfWork;

    public CreateTicketService(UserRepositoryPort users, DispatchServicePort dispatch, UnitOfWorkPort unitOfWork) {
        this.users = users;
        this.dispatch = dispatch;
        this.unitOfWork = unitOfWork;
    }

    @Override
    public Ticket create(String userEmail, String title, String description, TicketCategory category, TicketPriority priority) {
        UUID userId = users.findUserIdByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("Usuario autenticado no encontrado"));
        Instant now = Instant.now();
        UUID ticketId = UUID.randomUUID();
        DispatchServicePort.DispatchResult result = dispatch.dispatch(ticketId, category, priority);
        UUID technicianId = result.available() ? result.technicianId() : null;
        TicketStatus status = technicianId == null ? TicketStatus.ABIERTO : TicketStatus.ASIGNADO;
        Ticket ticket = new Ticket(ticketId, userId, technicianId, category, priority, status, title.trim(), description.trim(), now, SlaPolicy.deadlineFor(priority, now));
        TicketHistory history = new TicketHistory(ticketId, userId, null, status.name(), "Creación del ticket", now);
        unitOfWork.createTicketWithHistory(ticket, history);
        return ticket;
    }
}
