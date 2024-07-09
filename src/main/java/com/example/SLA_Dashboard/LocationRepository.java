package com.example.SLA_Dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT d FROM Location d WHERE d.Device_Id = :device_Id")
    List<Location> findByDevice_Id(String device_Id);
}
