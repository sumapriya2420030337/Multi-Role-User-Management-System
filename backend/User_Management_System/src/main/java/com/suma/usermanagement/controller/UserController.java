package com.suma.usermanagement.controller;

import com.suma.usermanagement.model.User;
import com.suma.usermanagement.model.Role;
import com.suma.usermanagement.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 📄 Pagination
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000") // 🔥 IMPORTANT
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // 🔐 CREATE USER
    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    // 📄 PAGINATION (FIXED ✅)
    @GetMapping
    public Page<User> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.getUsers(page, size);
    }

    // 🔹 FILTER BY ROLE
    @GetMapping("/role/{role}")
    public List<User> getByRole(@PathVariable Role role) {
        return service.getUsersByRole(role);
    }

    // 📊 ANALYTICS
    @GetMapping("/count-by-role")
    public List<Object[]> countByRole() {
        return service.getUserCountByRole();
    }

    // 🔹 DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "Deleted Successfully";
    }
}