package com.helpflow.tickets.infrastructure.output.persistence.repository;

import com.helpflow.tickets.infrastructure.output.persistence.entity.TicketHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTicketHistoryRepository extends JpaRepository<TicketHistoryJpaEntity, Long> {
}
