package com.example.SLA_Dashboard.Services;

import com.example.SLA_Dashboard.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SLA_Dashboard.Model.User;
import com.example.SLA_Dashboard.Repository.UserRepository;

@Service
public class Login_Service {

    @Autowired
    private UserRepository userRepository;

    public String verifyUser(User user) throws UserNotFoundException {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if(foundUser==null){
            throw new UserNotFoundException();
        }
        if ( !foundUser.getPassword().equals(user.getPassword())) {
            return "incorrect_password";
        }
        return "redirect:/dashboard";
    }
}
