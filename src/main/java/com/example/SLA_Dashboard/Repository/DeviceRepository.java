package com.example.SLA_Dashboard. Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.SLA_Dashboard.Model.Devices;

@Repository
public interface DeviceRepository extends JpaRepository<Devices, String>{




}

