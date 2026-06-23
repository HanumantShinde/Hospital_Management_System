package com.hospital.hospital_management.controller;

import com.hospital.hospital_management.dto.*;
import com.hospital.hospital_management.model.*;
import com.hospital.hospital_management.repository.*;
import com.hospital.hospital_management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AuthService authService;

    @GetMapping("/users")
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> new UserResponseDTO(u.getId(), u.getName(), u.getEmail(), u.getRole()))
                .collect(Collectors.toList());
    }

    @GetMapping("/doctors")
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(d -> new DoctorResponseDTO(
                        d.getId(), d.getUser().getName(), d.getUser().getEmail(),
                        d.getSpecialization(), d.getExperience(), d.getPhone()))
                .collect(Collectors.toList());
    }

    @GetMapping("/patients")
    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(p -> new PatientResponseDTO(
                        p.getId(), p.getUser().getName(), p.getUser().getEmail(),
                        p.getAge(), p.getBloodGroup(), p.getPhone(), p.getAddress()))
                .collect(Collectors.toList());
    }

    @PostMapping("/doctors")
    public UserResponseDTO addDoctor(@RequestBody User user) {
        user.setRole(Role.DOCTOR);
        return authService.registerUser(user);
    }

    @PostMapping("/patients")
    public UserResponseDTO addPatient(@RequestBody User user) {
        user.setRole(Role.PATIENT);
        return authService.registerUser(user);
    }

    @DeleteMapping("/doctors/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorRepository.deleteById(id);
        return "Doctor deleted successfully";
    }

    @DeleteMapping("/patients/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientRepository.deleteById(id);
        return "Patient deleted successfully";
    }
}