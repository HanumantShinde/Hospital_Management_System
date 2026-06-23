package com.hospital.hospital_management.dto;

import com.hospital.hospital_management.model.AppointmentStatus;
import lombok.Data;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AppointmentResponseDTO {
    private Long id;
    private String patientName;
    private String doctorName;
    private LocalDateTime appointmentDate;
    private AppointmentStatus status;
    private String notes;
}
