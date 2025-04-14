package com.example.Medical_Clinic_Proxy.feign;

import com.example.Medical_Clinic_Proxy.dto.PageableContentDTO;
import com.example.Medical_Clinic_Proxy.dto.Visit;
import com.example.Medical_Clinic_Proxy.dto.VisitFilterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@FeignClient(value = "medical-clinic", url = "http://app:8080/", configuration = FeignConfig.class)
public interface MedicalClinicClient {
    @GetMapping("visits")
    PageableContentDTO<Visit> getVisits(VisitFilterDTO filter, Pageable pageable);

    @PostMapping("visits/{id}/reserve")
    Visit reserveVisit(@PathVariable("id") Long id, @RequestParam("patientEmail") String patientEmail);

    @DeleteMapping
    void cancelVisit(@PathVariable Long id, @RequestParam String doctorEmail);
}