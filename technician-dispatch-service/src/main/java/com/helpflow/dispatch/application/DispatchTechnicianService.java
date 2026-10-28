package com.helpflow.dispatch.application;

import com.helpflow.dispatch.domain.DispatchDecision;
import com.helpflow.dispatch.domain.DispatchPolicy;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DispatchTechnicianService {
    private final DispatchPolicy policy;
    public DispatchTechnicianService() { this.policy=new DispatchPolicy(); }
    public DispatchDecision dispatch(UUID ticketId, String category, String priority) { return policy.decide(ticketId,category,priority); }
}
