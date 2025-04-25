package com.example.Medical_Clinic_Proxy.controller;


import com.example.Medical_Clinic_Proxy.dto.Doctor;
import com.example.Medical_Clinic_Proxy.dto.PageableContentDTO;
import com.example.Medical_Clinic_Proxy.dto.Visit;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import feign.FeignException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

@AutoConfigureWireMock(port = 8888)
@AutoConfigureMockMvc
@SpringBootTest(properties = {
        "spring.cloud.openfeign.circuitbreaker.enabled=false",
        "medical-clinic.url=http://localhost:8888"
})
public class VisitProxyControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setUp() {
        wireMockServer.start();
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void getVisits_shouldReturnVisits() throws Exception {
        List<Visit> visits = List.of(createVisit(1L), createVisit(2L));
        PageableContentDTO<Visit> pageableVisits = new PageableContentDTO<>(visits, 0, 10, 1, 2);

        wireMockServer.stubFor(get(urlPathEqualTo("/visits"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("onlyAvailable", equalTo("false"))
                .willReturn(okJson(objectMapper.writeValueAsString(pageableVisits))));

        mockMvc.perform(MockMvcRequestBuilders.get("/visits")
                        .param("page", "0")
                        .param("size", "10")
                        .param("onlyAvailable", "false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1));
    }

    @Test
    public void getVisits_shouldHandleServerError() {
        wireMockServer.stubFor(get(urlPathEqualTo("/visits"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("20"))
                .withQueryParam("onlyAvailable", equalTo("false"))
                .willReturn(serverError()));

        ServletException exception = assertThrows(ServletException.class, () ->
                mockMvc.perform(MockMvcRequestBuilders.get("/visits")
                        .param("page", "0")
                        .param("size", "20")
                        .param("onlyAvailable", "false")
                        .contentType(MediaType.APPLICATION_JSON)));

        assertInstanceOf(FeignException.class, exception.getCause());
    }

    @Test
    public void reserveVisit_shouldReturnVisit() throws Exception {
        Long id = 999L;
        String patientEmail = "test";
        Visit expectedVisit = createVisit(id);
        expectedVisit.setAvailable(false);
        wireMockServer.stubFor(post(urlPathEqualTo("/visits/" + id + "/reserve"))
                .withQueryParam("patientEmail", equalTo(patientEmail))
                .willReturn(okJson(objectMapper.writeValueAsString(expectedVisit))));

        mockMvc.perform(MockMvcRequestBuilders.post("/visits/" + id + "/reserve")
                        .param("patientEmail", patientEmail)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    public void reserveVisit_shouldHandleServerError() {
        wireMockServer.stubFor(post(urlPathEqualTo("/visits/" + 999L + "/reserve"))
                .withQueryParam("patientEmail", equalTo("test"))
                .willReturn(serverError()));

        ServletException exception = assertThrows(ServletException.class, () ->
                mockMvc.perform(MockMvcRequestBuilders.post("/visits/" + 999L + "/reserve")
                        .param("patientEmail", "test")
                        .contentType(MediaType.APPLICATION_JSON)));

        assertInstanceOf(FeignException.class, exception.getCause());
    }

    @Test
    public void cancelVisit_shouldReturn200() throws Exception {
        Long id = 999L;
        String doctorEmail = "test";
        wireMockServer.stubFor(delete(urlPathEqualTo("/visits/cancel/" + id))
                .withQueryParam("doctorEmail", equalTo(doctorEmail))
                .willReturn(ok()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/visits/cancel/" + id)
                        .param("doctorEmail", doctorEmail)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void cancelVisit_shouldHandleServerError() {
        wireMockServer.stubFor(delete(urlPathEqualTo("/visits/cancel/" + 999L))
                .withQueryParam("doctorEmail", equalTo("test"))
                .willReturn(serverError()));

        ServletException exception = assertThrows(ServletException.class, () ->
                mockMvc.perform(MockMvcRequestBuilders.delete("/visits/cancel/" + 999L)
                        .param("doctorEmail", "test")
                        .contentType(MediaType.APPLICATION_JSON)));

        assertInstanceOf(FeignException.class, exception.getCause());
    }

    private Visit createVisit(Long visitId) {
        return Visit.builder()
                .id(visitId)
                .doctor(createDoctor())
                .isAvailable(false)
                .startTime(LocalDateTime.now().plusHours(1).withMinute(0))
                .endTime(LocalDateTime.now().plusHours(2).withMinute(0))
                .build();
    }

    private Doctor createDoctor() {
        return Doctor.builder()
                .id(1L)
                .email("test@email.com")
                .facilityIds(new ArrayList<>())
                .build();
    }
}
