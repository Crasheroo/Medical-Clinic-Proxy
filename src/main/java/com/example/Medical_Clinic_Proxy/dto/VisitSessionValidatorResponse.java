package com.example.Medical_Clinic_Proxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitSessionValidatorResponse {
    private boolean valid;
    private String sessionId;
}
