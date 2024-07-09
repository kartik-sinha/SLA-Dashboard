package com.example.SLA_Dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceDataRepository extends JpaRepository<Device_Data, Long> {
    @Query("SELECT d FROM Device_Data d WHERE d.Device_Id = :device_Id")
    List<Device_Data> findByDevice_Id(String device_Id);
}
