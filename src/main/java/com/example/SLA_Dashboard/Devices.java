package com.example.SLA_Dashboard;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="devices")
public class Devices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long S_No;

    private String Device_Id;

    private String status;

    private double latitude;

    private double longitude;
}
