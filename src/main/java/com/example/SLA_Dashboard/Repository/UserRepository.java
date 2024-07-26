package com.example.SLA_Dashboard.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SLA_Dashboard.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
