package com.helpflow.tickets.infrastructure.output.persistence.adapter;

import com.helpflow.tickets.application.port.output.TicketRepositoryPort;
import com.helpflow.tickets.application.port.output.UnitOfWorkPort;
import com.helpflow.tickets.domain.model.*;
import com.helpflow.tickets.infrastructure.output.persistence.entity.*;
import com.helpflow.tickets.infrastructure.output.persistence.repository.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TicketPersistenceAdapter implements TicketRepositoryPort, UnitOfWorkPort {
    private final SpringDataTicketRepository tickets;
    private final SpringDataTicketHistoryRepository history;

    public TicketPersistenceAdapter(SpringDataTicketRepository tickets, SpringDataTicketHistoryRepository history) {
        this.tickets = tickets;
        this.history = history;
    }

    public Optional<Ticket> findById(UUID id) {
        return tickets.findById(id).map(TicketJpaEntity::toDomain);
    }

    public List<Ticket> findByUserId(UUID id) {
        return tickets.findAllByUserIdOrderByCreatedAtDesc(id).stream().map(TicketJpaEntity::toDomain).toList();
    }

    public List<Ticket> findByTechnicianId(UUID id) {
        return tickets.findAllByAssignedTechnicianIdOrderByCreatedAtDesc(id).stream().map(TicketJpaEntity::toDomain).toList();
    }

    public List<Ticket> findAll() {
        return tickets.findAll().stream().map(TicketJpaEntity::toDomain).toList();
    }

    @Transactional
    public void createTicketWithHistory(Ticket ticket, TicketHistory entry) {
        tickets.save(new TicketJpaEntity(ticket));
        history.save(new TicketHistoryJpaEntity(entry));
    }

    @Transactional
    public void updateTicketWithHistory(Ticket ticket, TicketHistory entry) {
        TicketJpaEntity entity = tickets.findById(ticket.id()).orElseThrow(() -> new java.util.NoSuchElementException("Ticket no encontrado"));
        entity.setStatus(ticket.status());
        tickets.save(entity);
        history.save(new TicketHistoryJpaEntity(entry));
    }

    public long countOverdue() {
        return tickets.countOverdue(Instant.now(), List.of(TicketStatus.CERRADO, TicketStatus.CANCELADO, TicketStatus.RESUELTO));
    }
}
