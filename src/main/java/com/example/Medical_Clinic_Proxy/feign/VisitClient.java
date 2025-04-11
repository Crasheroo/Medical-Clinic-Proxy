package com.example.Medical_Clinic_Proxy.feign;

import com.example.Medical_Clinic_Proxy.dto.Visit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(value = "visit",  url = "http://localhost:8080")
public interface VisitClient {
    @RequestMapping(method = RequestMethod.GET, value = "/visits/my-visits")
    List<Visit> getMyVisits(@RequestParam("patientEmail") String patientEmail);

    @RequestMapping(method = RequestMethod.GET, value = "/visits/doctor/{doctorId}/available")
    List<Visit> getDoctorAvailableVisits(@PathVariable("doctorId") Long doctorId);

    @RequestMapping(method = RequestMethod.GET, value = "/visits/available/by-specialty")
    List<Visit> getAvailableBySpecialtyAndDate(@RequestParam("specialty") String specialty, @RequestParam("date") LocalDateTime date);
}