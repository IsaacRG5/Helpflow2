package com.helpflow.tickets.domain.exception;

public class InvalidTicketTransitionException extends RuntimeException {
    public InvalidTicketTransitionException(String message) {
        super(message);
    }
}
