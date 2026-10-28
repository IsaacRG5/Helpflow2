package com.helpflow.tickets.domain.service;

import com.helpflow.tickets.domain.model.TicketStatus;

import java.util.Map;
import java.util.Set;

public final class TicketTransitionPolicy {
    private static final Map<TicketStatus, Set<TicketStatus>> ALLOWED = Map.of(
            TicketStatus.ABIERTO, Set.of(TicketStatus.ASIGNADO, TicketStatus.CANCELADO),
            TicketStatus.ASIGNADO, Set.of(TicketStatus.EN_PROGRESO, TicketStatus.ABIERTO, TicketStatus.CANCELADO),
            TicketStatus.EN_PROGRESO, Set.of(TicketStatus.RESUELTO, TicketStatus.ASIGNADO, TicketStatus.CANCELADO),
            TicketStatus.RESUELTO, Set.of(TicketStatus.CERRADO, TicketStatus.EN_PROGRESO),
            TicketStatus.CERRADO, Set.of(),
            TicketStatus.CANCELADO, Set.of()
    );

    private TicketTransitionPolicy() {
    }

    public static boolean canTransition(TicketStatus from, TicketStatus to) {
        return from == to || ALLOWED.getOrDefault(from, Set.of()).contains(to);
    }
}
