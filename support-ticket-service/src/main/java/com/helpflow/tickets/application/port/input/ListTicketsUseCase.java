package com.helpflow.tickets.application.port.input;

import com.helpflow.tickets.domain.model.Ticket;

import java.util.List;
import java.util.UUID;

public interface ListTicketsUseCase {
    List<Ticket> byUser(UUID userId);

    List<Ticket> byTechnician(UUID technicianId);

    List<Ticket> all();
}
