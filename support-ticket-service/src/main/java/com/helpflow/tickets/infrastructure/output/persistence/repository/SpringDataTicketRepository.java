package com.helpflow.tickets.infrastructure.output.persistence.repository;

import com.helpflow.tickets.domain.model.TicketStatus;
import com.helpflow.tickets.infrastructure.output.persistence.entity.TicketJpaEntity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataTicketRepository extends JpaRepository<TicketJpaEntity, UUID> {
    List<TicketJpaEntity> findAllByUserIdOrderByCreatedAtDesc(UUID userId);

    List<TicketJpaEntity> findAllByAssignedTechnicianIdOrderByCreatedAtDesc(UUID technicianId);

    @Query("select count(t) from TicketJpaEntity t where t.slaDeadline < :now and t.status not in :closedStatuses")
    long countOverdue(@Param("now") Instant now, @Param("closedStatuses") List<TicketStatus> closedStatuses);
}
