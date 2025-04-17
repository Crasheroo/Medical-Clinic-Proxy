package com.example.Medical_Clinic_Proxy.controller;

import com.example.Medical_Clinic_Proxy.dto.PageableContentDTO;
import com.example.Medical_Clinic_Proxy.dto.Visit;
import com.example.Medical_Clinic_Proxy.dto.VisitFilterDTO;
import com.example.Medical_Clinic_Proxy.service.ProxyVisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@Tag(name = "Visit operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("/visits")
public class VisitProxyController {
    private final ProxyVisitService proxyVisitService;

    @Operation(summary = "Get visits by parameter")
    @ApiResponse(responseCode = "200", description = "Visits found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PageableContentDTO.class))})
    @GetMapping
    public PageableContentDTO<Visit> getVisits(@SpringQueryMap VisitFilterDTO filter, @ParameterObject Pageable pageable) {
        return proxyVisitService.getVisits(filter, pageable);
    }

    @Operation(summary = "Reserve visit by visit Id and patient email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "patients found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "404", description = "patients not found")
    })
    @PatchMapping("/{id}/reserve")
    public Visit reserveVisit(@PathVariable Long id, @RequestParam String patientEmail) {
        return proxyVisitService.reserveVisit(id, patientEmail);
    }

    @Operation(summary = "Cancel visit by visit Id and patientEmail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visit canceled"),
            @ApiResponse(responseCode = "404", description = "Visit or patient not found")
    })
    @DeleteMapping("/{id}")
    public void cancelVisit(@PathVariable Long id, @RequestParam String patientEmail) {
        proxyVisitService.cancelVisit(id, patientEmail);
    }
}