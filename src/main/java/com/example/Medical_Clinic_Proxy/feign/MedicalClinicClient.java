package com.example.Medical_Clinic_Proxy.feign;

import com.example.Medical_Clinic_Proxy.dto.PageableContentDTO;
import com.example.Medical_Clinic_Proxy.dto.Visit;
import com.example.Medical_Clinic_Proxy.dto.VisitFilterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@FeignClient(value = "medical-clinic", url = "${medical-clinic.url}", configuration = FeignConfig.class)
public interface MedicalClinicClient {
    @GetMapping("visits")
    PageableContentDTO<Visit> getVisits(@SpringQueryMap VisitFilterDTO filter, Pageable pageable);

    @PostMapping("visits/{id}/reserve")
    Visit reserveVisit(@PathVariable("id") Long id, @RequestParam("patientEmail") String patientEmail);

    @DeleteMapping
    void cancelVisit(@PathVariable Long id, @RequestParam String doctorEmail);
}