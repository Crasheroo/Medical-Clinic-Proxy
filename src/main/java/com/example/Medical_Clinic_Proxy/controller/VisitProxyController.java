package com.example.Medical_Clinic_Proxy.controller;

import com.example.Medical_Clinic_Proxy.dto.PageableContentDTO;
import com.example.Medical_Clinic_Proxy.dto.Visit;
import com.example.Medical_Clinic_Proxy.dto.VisitFilterDTO;
import com.example.Medical_Clinic_Proxy.service.ProxyVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/visits")
public class VisitProxyController {
    private final ProxyVisitService proxyVisitService;

    @GetMapping
    public PageableContentDTO<Visit> getVisits(VisitFilterDTO filter, Pageable pageable) {
        return proxyVisitService.getVisits(filter, pageable);
    }

    @PostMapping("/{id}/reserve")
    public Visit reserveVisit(@PathVariable Long id, @RequestParam String patientEmail) {
        return proxyVisitService.reserveVisit(id, patientEmail);
    }

    @DeleteMapping("/{id}")
    public void cancelVisit(@PathVariable Long id, @RequestParam String patientEmail) {
        proxyVisitService.cancelVisit(id, patientEmail);
    }
}