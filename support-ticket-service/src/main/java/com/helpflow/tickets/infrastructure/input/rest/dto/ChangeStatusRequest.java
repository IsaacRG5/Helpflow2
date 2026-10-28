package com.helpflow.tickets.infrastructure.input.rest.dto;

import com.helpflow.tickets.domain.model.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChangeStatusRequest(@NotNull TicketStatus status, @NotBlank @Size(max = 1000) String observation) {
}
