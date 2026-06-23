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
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/profile")
    public PatientResponseDTO getProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Patient patient = patientRepository.findByUserId(user.getId());
        return new PatientResponseDTO(patient.getId(), user.getName(), user.getEmail(),
                patient.getAge(), patient.getBloodGroup(), patient.getPhone(), patient.getAddress());
    }

    @GetMapping("/doctors")
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(d -> new DoctorResponseDTO(
                        d.getId(), d.getUser().getName(), d.getUser().getEmail(),
                        d.getSpecialization(), d.getExperience(), d.getPhone()))
                .collect(Collectors.toList());
    }

    @PostMapping("/appointments")
    public Appointment bookAppointment(@RequestBody Appointment appointment) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Patient patient = patientRepository.findByUserId(user.getId());

        appointment.setPatient(patient);
        return appointmentService.bookAppointment(appointment);
    }

    @GetMapping("/appointments")
    public List<AppointmentResponseDTO> getMyAppointments() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Patient patient = patientRepository.findByUserId(user.getId());

        return appointmentService.getAppointmentsByPatient(patient.getId()).stream()
                .map(a -> new AppointmentResponseDTO(
                        a.getId(),
                        a.getPatient().getUser().getName(),
                        a.getDoctor().getUser().getName(),
                        a.getAppointmentDate(),
                        a.getStatus(),
                        a.getNotes()))
                .collect(Collectors.toList());
    }
}