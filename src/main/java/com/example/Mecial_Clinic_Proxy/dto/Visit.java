package com.example.Mecial_Clinic_Proxy.dto;

import java.time.LocalDateTime;

public class Visit {
    private Long id;
    private Doctor doctor;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isAvailable;
}
