package com.helpflow.tickets.application.port.output;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    Optional<UUID> findUserIdByEmail(String email);

    Optional<UUID> findTechnicianIdByUserId(UUID userId);
}
