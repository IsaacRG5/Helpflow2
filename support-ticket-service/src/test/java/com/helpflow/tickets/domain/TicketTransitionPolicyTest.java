package com.helpflow.tickets.domain;

import com.helpflow.tickets.domain.model.TicketStatus;
import com.helpflow.tickets.domain.service.TicketTransitionPolicy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketTransitionPolicyTest {
    @Test
    void allowsOpeningToAssignment() {
        assertTrue(TicketTransitionPolicy.canTransition(TicketStatus.ABIERTO, TicketStatus.ASIGNADO));
    }

    @Test
    void rejectsClosingAnOpenTicketDirectly() {
        assertFalse(TicketTransitionPolicy.canTransition(TicketStatus.ABIERTO, TicketStatus.CERRADO));
    }

    @Test
    void closedTicketIsTerminal() {
        assertFalse(TicketTransitionPolicy.canTransition(TicketStatus.CERRADO, TicketStatus.EN_PROGRESO));
    }
}
