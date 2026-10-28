package com.helpflow.tickets.application.port.output;

import com.helpflow.tickets.domain.model.Ticket;
import com.helpflow.tickets.domain.model.TicketHistory;

public interface UnitOfWorkPort {
    void createTicketWithHistory(Ticket ticket, TicketHistory history);

    void updateTicketWithHistory(Ticket ticket, TicketHistory history);
}
