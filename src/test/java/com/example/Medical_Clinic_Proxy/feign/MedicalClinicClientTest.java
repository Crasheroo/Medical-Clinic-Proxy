package com.example.Medical_Clinic_Proxy.feign;

import com.example.Medical_Clinic_Proxy.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import feign.FeignException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test") // po utworzeniu pliku application-"nazwa".properties tworze nowy profil
@AutoConfigureWireMock(port = 8888)
//@SpringBootTest(properties = {
//        "medical-clinic.url=http://localhost:8888"
//})
@SpringBootTest
public class MedicalClinicClientTest {
    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private MedicalClinicClient medicalClinicClient;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        wireMockServer.start();
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void getVisits_shouldReturn200_whenGetVisits() throws JsonProcessingException {
        // Given
        VisitFilterDTO filter = new VisitFilterDTO();
        Pageable pageable = PageRequest.of(0, 10);
        List<Visit> visits = List.of(createVisit(1L), createVisit(2L));
        PageableContentDTO<Visit> pageableVisits = new PageableContentDTO<>(visits, 0, 10, 1, 2);
        wireMockServer.stubFor(get(urlPathEqualTo("/visits"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("onlyAvailable", equalTo("false"))
                .willReturn(okJson(objectMapper.writeValueAsString(pageableVisits))));

        // When
        PageableContentDTO<Visit> result = medicalClinicClient.getVisits(filter, pageable);

        // Then
        assertNotNull(result);
        assertNotNull(result.getContent());
        assertEquals(2, result.getContent().size());
    }

    @Test
    void getVisits_shouldThrowFeignException_whenServerError() {
        VisitFilterDTO filter = new VisitFilterDTO();
        Pageable pageable = PageRequest.of(0, 10);

        wireMockServer.stubFor(get(urlPathEqualTo("/visits"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("onlyAvailable", equalTo("false"))
                .willReturn(serverError()));

        assertThrows(FeignException.class, () -> medicalClinicClient.getVisits(filter, pageable));
    }

    @Test
    void reserveVisit_shouldReturn200_whenReserveVisit() throws JsonProcessingException {
        // Given
        Long id = 999L;
        String patientEmail = "test";
        Visit expectedVisit = createVisit(id);
        expectedVisit.setAvailable(false);
        wireMockServer.stubFor(post(urlPathEqualTo("/visits/" + id + "/reserve"))
                .withQueryParam("patientEmail", equalTo(patientEmail))
                .willReturn(okJson(objectMapper.writeValueAsString(expectedVisit))));

        // When
        Visit result = medicalClinicClient.reserveVisit(id, patientEmail);

        // Then
        assertEquals(id, result.getId());
        assertFalse(result.isAvailable());
    }

    @Test
    void reserveVisit_shouldThrowFeignException_whenServerError() {
        Long id = 999L;
        String patientEmail = "test";
        wireMockServer.stubFor(post(urlPathEqualTo("/visits/" + id + "/reserve"))
                .willReturn(serverError()));

        assertThrows(FeignException.class, () -> medicalClinicClient.reserveVisit(id, patientEmail));
    }

    @Test
    void cancelVisit_shouldReturn200_whenCancelVisit() {
        // Given
        Long id = 999L;
        String doctorEmail = "test";
        wireMockServer.stubFor(delete(urlPathEqualTo("/visits/cancel/" + id))
                .withQueryParam("doctorEmail", equalTo(doctorEmail))
                .willReturn(ok()));

        // When
        assertDoesNotThrow(() -> medicalClinicClient.cancelVisit(id, doctorEmail));

        // Then
        wireMockServer.verify(deleteRequestedFor(urlPathEqualTo("/visits/cancel/" + id)).withQueryParam("doctorEmail", equalTo(doctorEmail)));
    }

    @Test
    void cancelVisit_shouldThrowFeignException_whenServerError() {
        Long id = 999L;
        String doctorEmail = "test";
        wireMockServer.stubFor(delete(urlPathEqualTo("/visits/cancel/" + id))
                .willReturn(serverError()));

        assertThrows(FeignException.class, () -> medicalClinicClient.cancelVisit(id, doctorEmail));
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