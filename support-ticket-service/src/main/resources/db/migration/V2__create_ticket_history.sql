CREATE TABLE ticket_history
(
    id                 BIGSERIAL PRIMARY KEY,
    ticket_id          UUID        NOT NULL REFERENCES tickets (id) ON DELETE CASCADE,
    changed_by_user_id UUID        NOT NULL REFERENCES app_users (id),
    previous_status    VARCHAR(20) NULL,
    new_status         VARCHAR(20) NOT NULL,
    observation        TEXT        NOT NULL,
    changed_at         TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX idx_ticket_history_ticket_date ON ticket_history (ticket_id, changed_at DESC);
