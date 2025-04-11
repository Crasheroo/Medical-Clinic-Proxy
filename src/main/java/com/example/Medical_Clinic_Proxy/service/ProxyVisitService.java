package com.example.Medical_Clinic_Proxy.service;

import com.example.Medical_Clinic_Proxy.dto.Visit;
import com.example.Medical_Clinic_Proxy.feign.VisitClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProxyVisitService {
    private final VisitClient visitClient;

    public List<Visit> getVisitsByPatient(String email) {
        return visitClient.getVisitsByPatient(email);
    }

    public List<Visit> getDoctorVisit(Long doctorId) {
        return visitClient.getDoctorAvailableVisits(doctorId);
    }

    public List<Visit> getVisitsBySpecialtyAndDay(String specialty, LocalDateTime date) {
        return visitClient.getAvailableBySpecialtyAndDate(specialty, date);
    }

    public Visit reserveVisit(Long id, String patientEmail) {
        return visitClient.reserveVisit(id, patientEmail);
    }
}
