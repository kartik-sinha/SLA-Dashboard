package com.example.SLA_Dashboard.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.SLA_Dashboard.Model.User;

@Repository
public interface SignUpRepository extends JpaRepository<User, String> {
}
