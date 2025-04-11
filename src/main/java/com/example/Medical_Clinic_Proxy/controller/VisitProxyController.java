package com.example.Medical_Clinic_Proxy.controller;

import com.example.Medical_Clinic_Proxy.dto.Visit;
import com.example.Medical_Clinic_Proxy.service.ProxyVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/visits")
public class VisitProxyController {
    private final ProxyVisitService proxyVisitService;

    @GetMapping("/my-visits")
    public List<Visit> getMyVisits(@RequestParam("patientEmail") String patientEmail) {
        return proxyVisitService.getPatientsVisit(patientEmail);
    }

    @GetMapping("/doctor/{doctorId}/available")
    public List<Visit> getAvailableVisits(@PathVariable Long doctorId) {
        return proxyVisitService.getDoctorVisit(doctorId);
    }

    @GetMapping("/available/by-specialty")
    public List<Visit> getAvailableBySpecialtyAndDate(
            @RequestParam String specialty,
            @RequestParam LocalDateTime date) {
        return proxyVisitService.getVisitsBySpecialtyAndDay(specialty, date);
    }
}