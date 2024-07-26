package com.example.SLA_Dashboard.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SLA_Dashboard.Model.User;
import com.example.SLA_Dashboard.Repository.UserRepository;

@Service
public class SignUpService {

    @Autowired
    private UserRepository userRepository;

    public String addUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "username_taken";
        }
        userRepository.save(user);
        return "user_added";
    }
}
