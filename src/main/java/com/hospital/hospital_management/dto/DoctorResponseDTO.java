package com.hospital.hospital_management.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class DoctorResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String specialization;
    private int experience;
    private String phone;
}
