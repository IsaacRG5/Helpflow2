package com.helpflow.dispatch.domain;

import java.util.Map;
import java.util.UUID;

public class DispatchPolicy {
    private static final Map<String,UUID> TECHNICIANS=Map.of(
        "HARDWARE",UUID.fromString("00000000-0000-0000-0000-000000000101"),
        "SOFTWARE",UUID.fromString("00000000-0000-0000-0000-000000000102"),
        "RED",UUID.fromString("00000000-0000-0000-0000-000000000103"),
        "ACCESOS",UUID.fromString("00000000-0000-0000-0000-000000000104"),
        "OTRO",UUID.fromString("00000000-0000-0000-0000-000000000105")
    );
    public DispatchDecision decide(UUID ticketId, String category, String priority) {
        UUID technician=TECHNICIANS.getOrDefault(category.toUpperCase(),TECHNICIANS.get("OTRO"));
        // Decisión determinista: la misma combinación de ticket/categoría/prioridad produce la misma respuesta.
        int stable=Math.floorMod((ticketId.toString()+category+priority).hashCode(),7);
        boolean available=stable != 0;
        int hours=switch (priority.toUpperCase()) { case "CRITICA" -> 2; case "ALTA" -> 8; case "MEDIA" -> 24; default -> 72; };
        return new DispatchDecision(technician,available,hours);
    }
}
