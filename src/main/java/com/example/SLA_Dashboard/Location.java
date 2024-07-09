package com.example.SLA_Dashboard;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Location {
    @Id
    public Long S_No;

    private String Device_Id;

    private double latitude;

    private double longitude;
}
