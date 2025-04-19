package com.example.Medical_Clinic_Proxy.feign;

import com.example.Medical_Clinic_Proxy.dto.PageableContentDTO;
import com.example.Medical_Clinic_Proxy.dto.Visit;
import com.example.Medical_Clinic_Proxy.dto.VisitFilterDTO;
import com.example.Medical_Clinic_Proxy.dto.VisitSessionValidatorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class MedicalClinicClientFallback implements MedicalClinicClient {
    @Override
    public PageableContentDTO<Visit> getVisits(VisitFilterDTO filter, Pageable pageable) {
        return null;
    }

    @Override
    public Visit reserveVisit(Long id, String patientEmail) {
        return null;
    }

    @Override
    public void cancelVisit(Long id, String doctorEmail) {}

    @Override
    public VisitSessionValidatorResponse validateSession(UUID sessionId) {
        log.info("[Fallback] validateSession");
        return new VisitSessionValidatorResponse(false, sessionId.toString());
    }
}
