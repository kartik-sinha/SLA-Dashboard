package com.example.SLA_Dashboard;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Login_Repository extends JpaRepository<User, String> {
}
