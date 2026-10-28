package com.helpflow.tickets.infrastructure.input.rest;

import com.helpflow.tickets.infrastructure.input.rest.dto.*;
import com.helpflow.tickets.infrastructure.output.persistence.entity.UserJpaEntity;
import com.helpflow.tickets.infrastructure.output.persistence.repository.SpringDataUserRepository;
import jakarta.validation.Valid;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final SpringDataUserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public AuthController(SpringDataUserRepository users, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> register(@Valid @RequestBody RegisterRequest request) {
        if (users.existsByEmailIgnoreCase(request.email()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        UserJpaEntity user = new UserJpaEntity(UUID.randomUUID(), request.name().trim(), request.email().trim().toLowerCase(),
                passwordEncoder.encode(request.password()), "ROLE_USUARIO", Instant.now());
        users.save(user);
        return Map.of("id", user.getId(), "name", user.getName(), "email", user.getEmail(), "role", user.getRole());
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        UserJpaEntity user = users.findByEmailIgnoreCase(request.email()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"));
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder().issuer("helpflow").issuedAt(now).expiresAt(now.plusSeconds(3600))
                .subject(user.getEmail()).claim("userId", user.getId().toString()).claim("roles", java.util.List.of(user.getRole())).build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        String token = jwtEncoder
                .encode(JwtEncoderParameters.from(header, claims))
                .getTokenValue();
        return new TokenResponse(token, "Bearer", 3600, user.getRole());
    }
}
