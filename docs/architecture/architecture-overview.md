# Arquitectura general

```mermaid
flowchart LR
    Client[Cliente / Postman / Swagger] -->|REST + JWT| API[support-ticket-service]
    API --> Domain[Dominio y casos de uso]
    API -->|Adaptador JPA| DB[(PostgreSQL)]
    API -->|HTTP| Dispatch[technician-dispatch-service]
    Dispatch --> Decision[Reglas simuladas deterministas]
```

`support-ticket-service` es el dueño de los tickets, usuarios, técnicos, historial y reglas de transición. `technician-dispatch-service` es stateless y simula una decisión de despacho.
