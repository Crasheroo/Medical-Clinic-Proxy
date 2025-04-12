package com.example.Medical_Clinic_Proxy.feign;

import com.example.Medical_Clinic_Proxy.dto.PageableContentDTO;
import com.example.Medical_Clinic_Proxy.dto.Visit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@FeignClient(value = "medical-clinic", url = "http://medicalclinic-app:8080/", configuration = FeignConfig.class)
public interface MedicalClinicClient {
    @GetMapping("visits")
    PageableContentDTO<Visit> getVisits(
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Boolean onlyAvailable,
            @RequestParam(required = false) String patientEmail,
            Pageable pageable);

    @PostMapping("visits/{id}/reserve")
    Visit reserveVisit(@PathVariable("id") Long id, @RequestParam("patientEmail") String patientEmail);
}