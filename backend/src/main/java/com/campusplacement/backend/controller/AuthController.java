package com.campusplacement.backend.controller;

import com.campusplacement.backend.model.User;
import com.campusplacement.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository userRepository;
    public AuthController(UserRepository userRepository) { this.userRepository = userRepository; }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        String name = body.get("name"), email = body.get("email"), password = body.get("password"), role = body.get("role");

        if (name == null || email == null || password == null || role == null) {
            response.put("success", false);
            response.put("message", "All fields are required.");
            return response;
        }
        if (userRepository.emailExists(email)) {
            response.put("success", false);
            response.put("message", "Email already registered.");
            return response;
        }
        User user = userRepository.createUser(name, email, password, role.toUpperCase());
        if (user == null) {
            response.put("success", false);
            response.put("message", "Registration failed.");
            return response;
        }
        response.put("success", true);
        response.put("message", "Registration successful.");
        response.put("userId", user.getId());
        response.put("role", user.getRole());
        return response;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        String email = body.get("email"), password = body.get("password");
        User user = userRepository.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            response.put("success", false);
            response.put("message", "Invalid email or password.");
            return response;
        }
        response.put("success", true);
        response.put("userId", user.getId());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("role", user.getRole());
        return response;
    }
}