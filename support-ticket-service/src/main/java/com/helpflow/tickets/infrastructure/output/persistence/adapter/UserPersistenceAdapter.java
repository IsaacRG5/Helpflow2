package com.helpflow.tickets.infrastructure.output.persistence.adapter;

import com.helpflow.tickets.application.port.output.UserRepositoryPort;
import com.helpflow.tickets.infrastructure.output.persistence.repository.SpringDataUserRepository;
import com.helpflow.tickets.infrastructure.output.persistence.repository.SpringDataTechnicianRepository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UserPersistenceAdapter implements UserRepositoryPort {
    private final SpringDataUserRepository users;
    private final SpringDataTechnicianRepository technicians;

    public UserPersistenceAdapter(SpringDataUserRepository users, SpringDataTechnicianRepository technicians) {
        this.users = users;
        this.technicians = technicians;
    }

    public Optional<UUID> findUserIdByEmail(String email) {
        return users.findByEmailIgnoreCase(email).map(u -> u.getId());
    }

    public Optional<UUID> findTechnicianIdByUserId(UUID userId) {
        return technicians.findByUserId(userId).map(t -> t.getId());
    }
}
