package com.hospital.hospital_management.controller;

import com.hospital.hospital_management.dto.UserResponseDTO;
import com.hospital.hospital_management.model.Role;
import com.hospital.hospital_management.model.User;
import com.hospital.hospital_management.security.JwtUtil;
import com.hospital.hospital_management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody User user) {
        user.setRole(Role.PATIENT);
        return authService.registerUser(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User loginRequest) {
        User user = authService.validateUser(loginRequest.getEmail(), loginRequest.getPassword());

        Map<String, String> response = new HashMap<>();

        if (user == null) {
            response.put("error", "Invalid email or password");
            return response;
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        response.put("token", token);
        response.put("role", user.getRole().name());

        return response;
    }
}
