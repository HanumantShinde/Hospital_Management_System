package com.hospital.hospital_management.dto;

import com.hospital.hospital_management.model.Role;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
