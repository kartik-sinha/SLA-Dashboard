package com.example.SLA_Dashboard. Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.SLA_Dashboard.Model.Devices;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Devices, String>{
    @Query("SELECT d.Device_Id FROM Devices d")
    List<String> findAllDeviceIds();




}

