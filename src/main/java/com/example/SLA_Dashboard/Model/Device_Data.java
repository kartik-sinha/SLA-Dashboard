package com.example.SLA_Dashboard.Model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Time;
import java.sql.Date;

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
    private Time time;
    private Date date;
}
