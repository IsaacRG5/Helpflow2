package com.helpflow.tickets.infrastructure.input.rest.dto;

import com.helpflow.tickets.domain.model.TicketCategory;
import com.helpflow.tickets.domain.model.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTicketRequest(@NotBlank @Size(max = 160) String title,
                                  @NotBlank @Size(max = 5000) String description,
                                  @NotNull TicketCategory category, @NotNull TicketPriority priority) {
}
