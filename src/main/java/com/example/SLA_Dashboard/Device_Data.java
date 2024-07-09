package com.example.SLA_Dashboard;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Device_Data {
    @Id
    private Long Entry_No;
    private String Device_Id;
    private String humidity;
    private String temperature;
}
