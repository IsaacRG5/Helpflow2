package com.helpflow.tickets.application.port.output;

import com.helpflow.tickets.domain.model.TicketCategory;
import com.helpflow.tickets.domain.model.TicketPriority;

import java.util.UUID;

public interface DispatchServicePort {
    DispatchResult dispatch(UUID ticketId, TicketCategory category, TicketPriority priority);

    record DispatchResult(UUID technicianId, boolean available, int estimatedHours) {
    }
}
