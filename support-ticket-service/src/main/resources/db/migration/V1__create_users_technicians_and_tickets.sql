CREATE TABLE app_users
(
    id            UUID PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    email         VARCHAR(180) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    role          VARCHAR(30)  NOT NULL DEFAULT 'ROLE_USUARIO',
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE technicians
(
    id                 UUID PRIMARY KEY,
    user_id            UUID NULL UNIQUE REFERENCES app_users(id),
    specialty          VARCHAR(30) NOT NULL,
    active             BOOLEAN     NOT NULL DEFAULT TRUE,
    max_active_tickets INTEGER     NOT NULL DEFAULT 5 CHECK (max_active_tickets > 0)
);

CREATE TABLE tickets
(
    id                     UUID PRIMARY KEY,
    user_id                UUID         NOT NULL REFERENCES app_users (id),
    assigned_technician_id UUID NULL REFERENCES technicians(id),
    category               VARCHAR(20)  NOT NULL CHECK (category IN ('HARDWARE', 'SOFTWARE', 'RED', 'ACCESOS', 'OTRO')),
    priority               VARCHAR(20)  NOT NULL CHECK (priority IN ('BAJA', 'MEDIA', 'ALTA', 'CRITICA')),
    status                 VARCHAR(20)  NOT NULL CHECK (status IN
                                                        ('ABIERTO', 'ASIGNADO', 'EN_PROGRESO', 'RESUELTO', 'CERRADO',
                                                         'CANCELADO')),
    title                  VARCHAR(160) NOT NULL,
    description            TEXT         NOT NULL,
    created_at             TIMESTAMPTZ  NOT NULL,
    sla_deadline           TIMESTAMPTZ  NOT NULL
);
CREATE INDEX idx_tickets_user_created ON tickets (user_id, created_at DESC);
CREATE INDEX idx_tickets_technician_status ON tickets (assigned_technician_id, status);
CREATE INDEX idx_tickets_sla_deadline ON tickets (sla_deadline);
