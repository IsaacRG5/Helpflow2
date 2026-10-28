package com.helpflow.tickets.infrastructure.output.dispatch;

import com.helpflow.tickets.application.port.output.DispatchServicePort;
import com.helpflow.tickets.domain.model.TicketCategory;
import com.helpflow.tickets.domain.model.TicketPriority;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class DispatchHttpClient implements DispatchServicePort {
    private final RestClient client;

    public DispatchHttpClient(RestClient.Builder builder, @Value("${app.dispatch.url}") String url) {
        this.client = builder.baseUrl(url).build();
    }

    @Override
    public DispatchResult dispatch(UUID ticketId, TicketCategory category, TicketPriority priority) {
        try {
            DispatchResponse result = client.post().uri("/technician-dispatch")
                    .body(new DispatchRequest(ticketId, category.name(), priority.name())).retrieve().body(DispatchResponse.class);
            if (result == null) throw new IllegalStateException("El servicio de despacho respondió vacío");
            return new DispatchResult(result.technicianId(), result.available(), result.estimatedHours());
        } catch (RestClientException ex) {
            throw new IllegalStateException("No se pudo contactar el servicio de despacho", ex);
        }
    }

    public record DispatchRequest(UUID ticketId, String category, String priority) {
    }

    public record DispatchResponse(UUID technicianId, boolean available, int estimatedHours) {
    }
}
