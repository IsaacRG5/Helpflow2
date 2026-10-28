package com.helpflow.tickets.application.port.input;

import com.helpflow.tickets.domain.model.Ticket;
import com.helpflow.tickets.domain.model.TicketStatus;

import java.util.UUID;

public interface ChangeTicketStatusUseCase {
    Ticket change(UUID ticketId, UUID actorUserId, TicketStatus newStatus, String observation);
}
