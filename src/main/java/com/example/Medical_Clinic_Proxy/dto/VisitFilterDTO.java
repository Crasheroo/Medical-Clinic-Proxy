package com.example.Medical_Clinic_Proxy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class VisitFilterDTO {
    private Long doctorId;
    private String specialty;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private boolean onlyAvailable;
    private String patientEmail;
}
