package com.example.Medical_Clinic_Proxy.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Visit {
    private Long id;
    private Doctor doctor;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isAvailable;
}
