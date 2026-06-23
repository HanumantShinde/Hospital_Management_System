package com.hospital.hospital_management.controller;

import com.hospital.hospital_management.dto.*;
import com.hospital.hospital_management.model.*;
import com.hospital.hospital_management.repository.*;
import com.hospital.hospital_management.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/profile")
    public DoctorResponseDTO getProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Doctor doctor = doctorRepository.findByUserId(user.getId());
        return new DoctorResponseDTO(doctor.getId(), user.getName(), user.getEmail(),
                doctor.getSpecialization(), doctor.getExperience(), doctor.getPhone());
    }

    @GetMapping("/appointments")
    public List<AppointmentResponseDTO> getMyAppointments() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Doctor doctor = doctorRepository.findByUserId(user.getId());

        return appointmentService.getAppointmentsByDoctor(doctor.getId()).stream()
                .map(a -> new AppointmentResponseDTO(
                        a.getId(),
                        a.getPatient().getUser().getName(),
                        a.getDoctor().getUser().getName(),
                        a.getAppointmentDate(),
                        a.getStatus(),
                        a.getNotes()))
                .collect(Collectors.toList());
    }

    @PutMapping("/appointments/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(id, appointment);
    }
}