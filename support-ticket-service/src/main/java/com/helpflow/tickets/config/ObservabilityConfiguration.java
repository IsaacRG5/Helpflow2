package com.helpflow.tickets.config;

import com.helpflow.tickets.infrastructure.output.persistence.adapter.TicketPersistenceAdapter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

@Configuration
public class ObservabilityConfiguration {
    private final MeterRegistry registry;
    private final TicketPersistenceAdapter tickets;

    public ObservabilityConfiguration(MeterRegistry registry, TicketPersistenceAdapter tickets) {
        this.registry = registry;
        this.tickets = tickets;
    }

    @PostConstruct
    public void registerMetrics() {
        Gauge.builder("helpflow.tickets.sla.overdue", tickets, TicketPersistenceAdapter::countOverdue)
                .description("Tickets con SLA vencido que no están cerrados, cancelados o resueltos").register(registry);
    }
}
