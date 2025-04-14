package com.example.Medical_Clinic_Proxy.service;

import com.example.Medical_Clinic_Proxy.dto.PageableContentDTO;
import com.example.Medical_Clinic_Proxy.dto.Visit;
import com.example.Medical_Clinic_Proxy.dto.VisitFilterDTO;
import com.example.Medical_Clinic_Proxy.feign.MedicalClinicClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProxyVisitService {
    private final MedicalClinicClient medicalClinicClient;

    public PageableContentDTO<Visit> getVisits(VisitFilterDTO filter, Pageable pageable) {
        return medicalClinicClient.getVisits(filter, pageable);
    }

    public Visit reserveVisit(Long id, String patientEmail) {
        return medicalClinicClient.reserveVisit(id, patientEmail);
    }

    public void cancelVisit(Long id, String doctorEmail) {
        medicalClinicClient.cancelVisit(id, doctorEmail);
    }
}
