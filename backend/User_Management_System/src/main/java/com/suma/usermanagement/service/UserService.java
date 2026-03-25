package com.suma.usermanagement.service;

import com.suma.usermanagement.model.User;
import com.suma.usermanagement.model.Role;
import com.suma.usermanagement.repository.UserRepository;
import com.suma.usermanagement.repository.RoleRepository;

import org.springframework.stereotype.Service;

import java.util.List;

// 🔐 Password encoding
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

// 📄 Pagination + Sorting
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
public class UserService {

    private final UserRepository repo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // 🔐 CREATE USER (with role mapping + encryption)
    public User createUser(User user) {

        // 🔥 get role from DB
        Role role = roleRepo.findByName(user.getRole().getName());
        user.setRole(role);

        // 🔐 encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return repo.save(user);
    }

    // 🔹 FILTER BY ROLE
    public List<User> getUsersByRole(Role role) {
        return repo.findByRole(role);
    }

    // 🔹 DELETE USER (with exception handling)
    public void deleteUser(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        repo.deleteById(id);
    }

    // 📊 ANALYTICS
    public List<Object[]> getUserCountByRole() {
        return repo.countUsersByRole();
    }

    // 📄 PAGINATION + SORTING
    public Page<User> getUsers(int page, int size) {
        return repo.findAll(PageRequest.of(page, size, Sort.by("name").ascending()));
    }
}