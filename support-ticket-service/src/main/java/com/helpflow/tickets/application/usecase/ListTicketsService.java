package com.helpflow.tickets.application.usecase;

import com.helpflow.tickets.application.port.input.ListTicketsUseCase;
import com.helpflow.tickets.application.port.output.TicketRepositoryPort;
import com.helpflow.tickets.domain.model.Ticket;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ListTicketsService implements ListTicketsUseCase {
    private final TicketRepositoryPort tickets;

    public ListTicketsService(TicketRepositoryPort tickets) {
        this.tickets = tickets;
    }

    public List<Ticket> byUser(UUID id) {
        return tickets.findByUserId(id);
    }

    public List<Ticket> byTechnician(UUID id) {
        return tickets.findByTechnicianId(id);
    }

    public List<Ticket> all() {
        return tickets.findAll();
    }
}
