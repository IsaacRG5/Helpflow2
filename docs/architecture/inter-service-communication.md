# Comunicación entre servicios

1. La API principal valida el usuario y los datos recibidos.
2. El caso de uso calcula la fecha límite SLA y solicita una decisión HTTP al servicio de despacho.
3. El servicio de despacho responde con `technicianId`, `available` y `estimatedHours`.
4. La API principal determina el estado inicial y guarda ticket e historial en una sola transacción.

Endpoint interno: `POST /technician-dispatch`.
