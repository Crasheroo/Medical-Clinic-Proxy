package com.example.Medical_Clinic_Proxy.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Patient {
    private Long id;
    private String email;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;
}
