package com.example.Medical_Clinic_Proxy.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Doctor {
    private Long id;
    private String email;
    private String specialty;
    private List<Long> facilityIds;
}
