package com.helpflow.dispatch.domain;

import java.util.UUID;

public record DispatchDecision(UUID technicianId, boolean available, int estimatedHours) { }
