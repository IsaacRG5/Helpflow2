# Ciclo de vida del ticket

```mermaid
stateDiagram-v2
    [*] --> ABIERTO
    ABIERTO --> ASIGNADO
    ABIERTO --> CANCELADO
    ASIGNADO --> EN_PROGRESO
    ASIGNADO --> ABIERTO
    ASIGNADO --> CANCELADO
    EN_PROGRESO --> RESUELTO
    EN_PROGRESO --> ASIGNADO
    EN_PROGRESO --> CANCELADO
    RESUELTO --> CERRADO
    RESUELTO --> EN_PROGRESO
    CERRADO --> [*]
    CANCELADO --> [*]
```
