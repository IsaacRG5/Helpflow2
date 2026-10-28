package com.helpflow.tickets.infrastructure.input.rest.dto;

public record TokenResponse(String token, String tokenType, long expiresInSeconds, String role) {
}
