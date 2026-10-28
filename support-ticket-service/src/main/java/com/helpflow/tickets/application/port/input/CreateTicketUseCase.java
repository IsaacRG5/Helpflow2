package com.helpflow.tickets.application.port.input;

import com.helpflow.tickets.domain.model.Ticket;
import com.helpflow.tickets.domain.model.TicketCategory;
import com.helpflow.tickets.domain.model.TicketPriority;

public interface CreateTicketUseCase {
    Ticket create(String userEmail, String title, String description, TicketCategory category, TicketPriority priority);
}
