package com.hospital.hospital_management.repository;

import com.hospital.hospital_management.model.Role;
import com.hospital.hospital_management.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_shouldReturnUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@hospital.com");
        user.setPassword("encoded");
        user.setRole(Role.PATIENT);

        userRepository.save(user);

        User found = userRepository.findByEmail("test@hospital.com");

        assertNotNull(found);
        assertEquals("Test User", found.getName());
        assertEquals(Role.PATIENT, found.getRole());
    }

    @Test
    void findByEmail_shouldReturnNullWhenNotExists() {
        User found = userRepository.findByEmail("notexist@hospital.com");
        assertNull(found);
    }
}