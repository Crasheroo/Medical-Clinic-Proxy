package com.example.Medical_Clinic_Proxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class VisitFilterDTO {
    @Schema(description = "ID of the doctor", example = "1")
    private Long doctorId;

    @Schema(description = "Medical specialty", example = "cardiology")
    private String specialty;

    @Schema(description = "Date of visit (YYYY-MM-DD)", example = "2000-12-31")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Schema(description = "Show only available time slots", example = "true")
    private boolean onlyAvailable;

    @Schema(description = "Email of the patient", example = "patient@example.com")
    private String patientEmail;
}
