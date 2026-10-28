package com.helpflow.tickets.infrastructure.output.persistence.repository;

import com.helpflow.tickets.infrastructure.output.persistence.entity.UserJpaEntity;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, UUID> {
    Optional<UserJpaEntity> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
