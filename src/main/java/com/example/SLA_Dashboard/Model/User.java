package com.example.SLA_Dashboard.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {

    @Id
    private String username;
    private String phone_number;
    private String email;
    private String name;
    private String password;
}
