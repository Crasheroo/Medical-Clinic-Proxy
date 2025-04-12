package com.example.Medical_Clinic_Proxy.controller;

import com.example.Medical_Clinic_Proxy.dto.PageableContentDTO;
import com.example.Medical_Clinic_Proxy.dto.Visit;
import com.example.Medical_Clinic_Proxy.service.ProxyVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/visits")
public class VisitProxyController {
    private final ProxyVisitService proxyVisitService;

    @GetMapping
    public PageableContentDTO<Visit> getVisits(
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false, defaultValue = "false") boolean onlyAvailable,
            @RequestParam(required = false) String patientEmail,
            Pageable pageable) {

        return proxyVisitService.getVisits(
                doctorId,
                specialty,
                date,
                onlyAvailable,
                patientEmail,
                pageable
        );
    }

    @PostMapping("/{id}/reserve")
    public Visit reserveVisit(@PathVariable Long id, @RequestParam String patientEmail) {
        return proxyVisitService.reserveVisit(id, patientEmail);
    }
}