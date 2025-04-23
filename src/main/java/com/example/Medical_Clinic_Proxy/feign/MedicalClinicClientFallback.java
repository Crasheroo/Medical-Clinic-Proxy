package com.example.Medical_Clinic_Proxy.feign;

import com.example.Medical_Clinic_Proxy.dto.PageableContentDTO;
import com.example.Medical_Clinic_Proxy.dto.Visit;
import com.example.Medical_Clinic_Proxy.dto.VisitFilterDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MedicalClinicClientFallback implements MedicalClinicClient {
    @Override
    public PageableContentDTO<Visit> getVisits(VisitFilterDTO filter, Pageable pageable) {
        log.warn("Fallback: Returning empty list");
        return new PageableContentDTO<>();
    }

    @Override
    public Visit reserveVisit(Long id, String patientEmail) {
        log.error("Fallback: Could not reserve visit {} for patient {}", id, patientEmail);
        throw new RuntimeException("Service error");
    }

    @Override
    public void cancelVisit(Long id, String doctorEmail) {
        log.error("Fallback: Could not cancel visit {} for doctor {}", id, doctorEmail);
    }
}
