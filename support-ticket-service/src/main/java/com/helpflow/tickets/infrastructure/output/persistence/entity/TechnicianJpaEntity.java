package com.helpflow.tickets.infrastructure.output.persistence.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "technicians")
public class TechnicianJpaEntity {
    @Id
    private UUID id;
    @Column(name = "user_id")
    private UUID userId;
    @Column(nullable = false, length = 30)
    private String specialty;
    @Column(nullable = false)
    private boolean active;
    @Column(name = "max_active_tickets", nullable = false)
    private int maxActiveTickets;

    protected TechnicianJpaEntity() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }
}
