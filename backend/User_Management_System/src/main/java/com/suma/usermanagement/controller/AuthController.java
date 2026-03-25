package com.suma.usermanagement.controller;

import com.suma.usermanagement.model.User;
import com.suma.usermanagement.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@CrossOrigin(origins = "http://localhost:3000") // 🔥 ADD THIS
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 🔐 LOGIN API
    @PostMapping("/login")
    public String login(@RequestBody User loginUser) {

        User user = userRepository.findByEmail(loginUser.getEmail());

        if (user == null) {
            return "User not found";
        }

        if (passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            return "Login Successful";
        } else {
            return "Invalid Password";
        }
    }
}