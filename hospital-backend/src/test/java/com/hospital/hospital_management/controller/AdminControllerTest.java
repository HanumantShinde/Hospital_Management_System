package com.hospital.hospital_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.hospital_management.dto.UserResponseDTO;
import com.hospital.hospital_management.model.Role;
import com.hospital.hospital_management.model.User;
import com.hospital.hospital_management.repository.DoctorRepository;
import com.hospital.hospital_management.repository.PatientRepository;
import com.hospital.hospital_management.repository.UserRepository;
import com.hospital.hospital_management.security.JwtAuthFilter;
import com.hospital.hospital_management.security.JwtUtil;
import com.hospital.hospital_management.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)   // ← Important: disables security filters
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private DoctorRepository doctorRepository;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private AuthService authService;

    // Mock the security related classes so Spring doesn't complain
    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers_shouldReturnList() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Admin User");
        user.setEmail("admin@hospital.com");
        user.setRole(Role.ADMIN);

        when(userRepository.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Admin User"))
                .andExpect(jsonPath("$[0].role").value("ADMIN"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addDoctor_shouldSetRoleToDoctor() throws Exception {
        User request = new User();
        request.setName("Dr. Smith");
        request.setEmail("smith@hospital.com");
        request.setPassword("password123");

        UserResponseDTO response = new UserResponseDTO(1L, "Dr. Smith", "smith@hospital.com", Role.DOCTOR);

        when(authService.registerUser(any(User.class))).thenReturn(response);

        mockMvc.perform(post("/api/admin/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("DOCTOR"))
                .andExpect(jsonPath("$.name").value("Dr. Smith"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteDoctor_shouldReturnSuccessMessage() throws Exception {
        mockMvc.perform(delete("/api/admin/doctors/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Doctor deleted successfully"));
    }
}