package com.hospital.hospital_management.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class PatientResponseDTO {
    private Long id;
    private String name;
    private String email;
    private int age;
    private String bloodGroup;
    private String phone;
    private String address;
}
