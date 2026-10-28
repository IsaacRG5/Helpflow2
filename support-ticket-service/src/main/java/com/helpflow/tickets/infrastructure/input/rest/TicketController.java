package com.helpflow.tickets.infrastructure.input.rest;

import com.helpflow.tickets.application.port.input.*;
import com.helpflow.tickets.application.port.output.*;
import com.helpflow.tickets.domain.model.*;
import com.helpflow.tickets.infrastructure.input.rest.dto.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class TicketController {
    private final CreateTicketUseCase createTicket;
    private final ListTicketsUseCase listTickets;
    private final ChangeTicketStatusUseCase changeStatus;
    private final TicketRepositoryPort tickets;
    private final UserRepositoryPort users;

    public TicketController(CreateTicketUseCase createTicket, ListTicketsUseCase listTickets, ChangeTicketStatusUseCase changeStatus,
                            TicketRepositoryPort tickets, UserRepositoryPort users) {
        this.createTicket = createTicket;
        this.listTickets = listTickets;
        this.changeStatus = changeStatus;
        this.tickets = tickets;
        this.users = users;
    }

    @PostMapping("/tickets")
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse create(@Valid @RequestBody CreateTicketRequest request, Authentication auth) {
        return TicketResponse.from(createTicket.create(auth.getName(), request.title(), request.description(), request.category(), request.priority()));
    }

    @GetMapping("/tickets/me")
    public List<TicketResponse> mine(Authentication auth) {
        UUID userId = currentUserId();
        if (hasRole("ROLE_ADMIN")) return listTickets.all().stream().map(TicketResponse::from).toList();
        if (hasRole("ROLE_TECNICO")) {
            UUID technicianId = users.findTechnicianIdByUserId(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "No existe una ficha de técnico vinculada a la cuenta"));
            return listTickets.byTechnician(technicianId).stream().map(TicketResponse::from).toList();
        }
        return listTickets.byUser(userId).stream().map(TicketResponse::from).toList();
    }

    @GetMapping("/tickets/{id}")
    public TicketResponse get(@PathVariable UUID id) {
        Ticket ticket = tickets.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket no encontrado"));
        assertCanSee(ticket);
        return TicketResponse.from(ticket);
    }

    @PatchMapping("/tickets/{id}/status")
    public TicketResponse change(@PathVariable UUID id, @Valid @RequestBody ChangeStatusRequest request) {
        Ticket old = tickets.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket no encontrado"));
        assertCanManage(old);
        return TicketResponse.from(changeStatus.change(id, currentUserId(), request.status(), request.observation()));
    }

    @GetMapping("/technician/tickets")
    public List<TicketResponse> assignedToMe() {
        UUID technicianId = users.findTechnicianIdByUserId(currentUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "No existe una ficha de técnico vinculada a la cuenta"));
        return listTickets.byTechnician(technicianId).stream().map(TicketResponse::from).toList();
    }

    @GetMapping("/admin/tickets")
    public List<TicketResponse> all() {
        return listTickets.all().stream().map(TicketResponse::from).toList();
    }

    private void assertCanSee(Ticket ticket) {
        if (hasRole("ROLE_ADMIN")) return;
        if (hasRole("ROLE_USUARIO") && ticket.userId().equals(currentUserId())) return;
        if (hasRole("ROLE_TECNICO") && isAssignedTechnician(ticket)) return;
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para consultar este ticket");
    }

    private void assertCanManage(Ticket ticket) {
        // Los usuarios finales solo consultan y crean tickets; la gestión corresponde al técnico asignado o al admin.
        if (hasRole("ROLE_ADMIN")) return;
        if (hasRole("ROLE_TECNICO") && isAssignedTechnician(ticket)) return;
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo el técnico asignado o un administrador puede cambiar el estado");
    }

    private boolean isAssignedTechnician(Ticket ticket) {
        return users.findTechnicianIdByUserId(currentUserId()).map(id -> id.equals(ticket.assignedTechnicianId())).orElse(false);
    }

    private UUID currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt)
            return UUID.fromString(jwt.getClaimAsString("userId"));
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token de acceso inválido");
    }

    private boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> role.equals(a.getAuthority()));
    }
}
