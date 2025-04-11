package com.example.Medical_Clinic_Proxy.feign;

import com.example.Medical_Clinic_Proxy.dto.Visit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(value = "medical-clinic-visits", url = "http://localhost:8080/visits")
public interface VisitClient {
    @GetMapping("/my-visits")
    List<Visit> getVisitsByPatient(@RequestParam("patientEmail") String patientEmail);

    @GetMapping("/doctor/{doctorId}/available")
    List<Visit> getDoctorAvailableVisits(@PathVariable("doctorId") Long doctorId);

    @GetMapping("/available/by-specialty")
    List<Visit> getAvailableBySpecialtyAndDate(@RequestParam("specialty") String specialty, @RequestParam("date") LocalDateTime date);

    @PostMapping(value = "/{id}/reserve")
    Visit reserveVisit(@PathVariable("id") Long id, @RequestParam("patientEmail") String patientEmail);
}