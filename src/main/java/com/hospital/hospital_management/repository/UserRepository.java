package com.hospital.hospital_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.hospital_management.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

