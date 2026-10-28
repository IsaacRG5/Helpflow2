package com.helpflow.tickets.infrastructure.output.persistence.entity;

import com.helpflow.tickets.domain.model.*;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tickets")
public class TicketJpaEntity {
    @Id
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "assigned_technician_id")
    private UUID assignedTechnicianId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TicketCategory category;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TicketPriority priority;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TicketStatus status;
    @Column(nullable = false, length = 160)
    private String title;
    @Column(nullable = false, columnDefinition = "text")
    private String description;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "sla_deadline", nullable = false)
    private Instant slaDeadline;

    protected TicketJpaEntity() {
    }

    public TicketJpaEntity(Ticket t) {
        id = t.id();
        userId = t.userId();
        assignedTechnicianId = t.assignedTechnicianId();
        category = t.category();
        priority = t.priority();
        status = t.status();
        title = t.title();
        description = t.description();
        createdAt = t.createdAt();
        slaDeadline = t.slaDeadline();
    }

    public Ticket toDomain() {
        return new Ticket(id, userId, assignedTechnicianId, category, priority, status, title, description, createdAt, slaDeadline);
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getAssignedTechnicianId() {
        return assignedTechnicianId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
