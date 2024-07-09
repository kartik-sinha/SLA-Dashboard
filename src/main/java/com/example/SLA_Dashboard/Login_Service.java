package com.example.SLA_Dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Login_Service {

    @Autowired
    private Login_Repository loginRepository;

    public String verifyUser(User user) {
        String name=user.getName();
        String password=user.getPassword();
        String email=user.getEmail();
        String number=user.getPhone_number();

        User dbUser=loginRepository.findById(number).orElse(null);
        if(dbUser==null){
            return "error";
        } else if ((dbUser.getPassword().equalsIgnoreCase(password)) &&
                ((dbUser.getName().equalsIgnoreCase(name))) &&
                ((dbUser.getEmail().equalsIgnoreCase(email))) ) {
            return "dashboard";
        }else{
            return "error";
        }


    }
}
