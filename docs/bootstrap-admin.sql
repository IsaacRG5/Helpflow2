-- Entorno local únicamente. Registra la cuenta por /auth/register primero y sustituye el correo.
UPDATE app_users SET role = 'ROLE_ADMIN' WHERE email = 'admin@example.com';

-- Consulta la identidad del usuario:
SELECT id, name, email, role FROM app_users ORDER BY created_at DESC;

-- Para vincular un usuario técnico existente, sustituye ambos UUID:
-- UPDATE app_users SET role = 'ROLE_TECNICO' WHERE id = 'UUID-USUARIO';
-- UPDATE technicians SET user_id = 'UUID-USUARIO' WHERE id = 'UUID-TECNICO';
