package com.hospital.hospital_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.hospital_management.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByUserId(Long userId);
}
