package com.helpflow.tickets.infrastructure.output.persistence.repository;

import com.helpflow.tickets.infrastructure.output.persistence.entity.TechnicianJpaEntity;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTechnicianRepository extends JpaRepository<TechnicianJpaEntity, UUID> {
    Optional<TechnicianJpaEntity> findByUserId(UUID userId);
}
