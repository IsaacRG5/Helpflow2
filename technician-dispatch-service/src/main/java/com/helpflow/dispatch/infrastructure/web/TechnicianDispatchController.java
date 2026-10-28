package com.helpflow.dispatch.infrastructure.web;

import com.helpflow.dispatch.application.DispatchTechnicianService;
import com.helpflow.dispatch.domain.DispatchDecision;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TechnicianDispatchController {
    private final DispatchTechnicianService dispatch;
    public TechnicianDispatchController(DispatchTechnicianService dispatch) { this.dispatch=dispatch; }

    @PostMapping("/technician-dispatch")
    @ResponseStatus(HttpStatus.OK)
    public DispatchResponse dispatch(@Valid @RequestBody DispatchRequest request) {
        DispatchDecision result=dispatch.dispatch(request.ticketId(),request.category(),request.priority());
        return new DispatchResponse(result.technicianId(),result.available(),result.estimatedHours());
    }
    public record DispatchRequest(@NotNull UUID ticketId,@NotBlank String category,@NotBlank String priority) { }
    public record DispatchResponse(UUID technicianId,boolean available,int estimatedHours) { }
}
