package com.example.Mecial_Clinic_Proxy.service;

import com.example.Mecial_Clinic_Proxy.dto.Visit;
import com.example.Mecial_Clinic_Proxy.feign.VisitClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProxyVisitService {
    private final VisitClient visitClient;

    public List<Visit> getPatientsVisit(String email) {
        return visitClient.getMyVisits(email);
    }

    public List<Visit> getDoctorVisit(Long doctorId) {
        return visitClient.getAvailableByDoctor(doctorId);
    }

    public List<Visit> getVisitsBySpecialtyAndDay(String specialty, LocalDateTime date) {
        return visitClient.getAvailableBySpecialtyAndDate(specialty, date);
    }
}
