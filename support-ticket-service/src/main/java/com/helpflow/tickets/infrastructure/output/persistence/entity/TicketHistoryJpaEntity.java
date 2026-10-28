package com.helpflow.tickets.infrastructure.output.persistence.entity;

import com.helpflow.tickets.domain.model.TicketHistory;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ticket_history")
public class TicketHistoryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ticket_id", nullable = false)
    private UUID ticketId;
    @Column(name = "changed_by_user_id", nullable = false)
    private UUID changedByUserId;
    @Column(name = "previous_status", length = 20)
    private String previousStatus;
    @Column(name = "new_status", nullable = false, length = 20)
    private String newStatus;
    @Column(nullable = false, columnDefinition = "text")
    private String observation;
    @Column(name = "changed_at", nullable = false)
    private Instant changedAt;

    protected TicketHistoryJpaEntity() {
    }

    public TicketHistoryJpaEntity(TicketHistory h) {
        ticketId = h.ticketId();
        changedByUserId = h.changedByUserId();
        previousStatus = h.previousStatus();
        newStatus = h.newStatus();
        observation = h.observation();
        changedAt = h.changedAt();
    }
}
