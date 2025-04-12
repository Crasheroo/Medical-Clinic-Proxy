package com.example.Medical_Clinic_Proxy.service;

import com.example.Medical_Clinic_Proxy.dto.PageableContentDTO;
import com.example.Medical_Clinic_Proxy.dto.Visit;
import com.example.Medical_Clinic_Proxy.feign.VisitClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProxyVisitService {
    private final VisitClient visitClient;

    public PageableContentDTO<Visit> getVisits(Long doctorId, String specialty, LocalDate date,
                                               boolean onlyAvailable, String patientEmail, Pageable pageable) {
        return visitClient.getVisits(
                doctorId,
                specialty,
                date != null ? date.toString() : null,
                onlyAvailable,
                patientEmail,
                pageable
        );
    }

    public Visit reserveVisit(Long id, String patientEmail) {
        return visitClient.reserveVisit(id, patientEmail);
    }
}
