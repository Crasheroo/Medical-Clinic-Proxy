package com.example.Mecial_Clinic_Proxy.feign;

import com.example.Mecial_Clinic_Proxy.dto.Visit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "visit-client")
public interface VisitClient {
    @GetMapping("/visits/my")
    List<Visit> getMyVisits(@RequestParam("patientEmail") String patientEmail);

    @GetMapping("/visits/doctor/{doctorId}/available")
    List<Visit> getAvailableByDoctor(@PathVariable("doctorId") Long doctorId);

    @GetMapping("/visits/available/by-specialty")
    List<Visit> getAvailableBySpecialtyAndDate(@RequestParam("specialty") String specialty, @RequestParam("date") LocalDateTime date);
}
