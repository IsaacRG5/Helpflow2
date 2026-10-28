package com.helpflow.dispatch.domain;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DispatchPolicyTest {
    @Test void dispatchIsDeterministic() {
        DispatchPolicy policy=new DispatchPolicy(); UUID id=UUID.fromString("10000000-0000-0000-0000-000000000001");
        assertEquals(policy.decide(id,"HARDWARE","ALTA"),policy.decide(id,"HARDWARE","ALTA"));
    }
    @Test void selectsTechnicianByCategory() {
        DispatchDecision result=new DispatchPolicy().decide(UUID.randomUUID(),"RED","MEDIA");
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000103"),result.technicianId());
        assertEquals(24,result.estimatedHours());
    }
}
