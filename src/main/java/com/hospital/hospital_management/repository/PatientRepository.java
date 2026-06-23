package com.hospital.hospital_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.hospital_management.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByUserId(Long userId);
}
