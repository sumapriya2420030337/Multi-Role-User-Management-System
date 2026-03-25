package com.suma.usermanagement.repository;

import com.suma.usermanagement.model.User;
import com.suma.usermanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // 🔹 Filter users by role
    List<User> findByRole(Role role);

    // 🔹 Login support
    User findByEmail(String email);

    // 🔥 Analytics (COUNT BY ROLE)
    @Query("SELECT u.role, COUNT(u) FROM User u GROUP BY u.role")
    List<Object[]> countUsersByRole();
}