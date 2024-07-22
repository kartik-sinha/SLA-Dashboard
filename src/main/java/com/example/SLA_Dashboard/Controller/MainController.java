package com.example.SLA_Dashboard.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.SLA_Dashboard.Repository.*;
import com.example.SLA_Dashboard.Model.*;
import com.example.SLA_Dashboard.Services.*;

@Controller
public class MainController {

    @Autowired
    private Login_Service loginService;

    @Autowired
    private MapService mapService;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    DeviceDataRepository deviceDataRepository;

    @Autowired
    LocationRepository locationRepository;

    //used to get the login page
    @GetMapping("/login")
    public String mainPage() {
        return "login";
    }

    //used to verify the user credentials and redirect to the dashboard
    @PostMapping("/dashboard")
    public String redirectPage(@ModelAttribute User user) {
        return loginService.verifyUser(user);
    }

    //used to get the latest locations of the devices
    @GetMapping("/devices")
    @ResponseBody
    public List<Devices> getAllLocations() {
        return deviceRepository.findAll();
    }

    //used to the list of locations for a specific device id
    @GetMapping("/devices/{id}")
    @ResponseBody
    public List<Location> getAllLocations(@PathVariable String id) {
        String DeviceId="Device "+id;
        return locationRepository.findByDevice_Id(DeviceId);
    }

    //used to get the device data for a specific device id
    @GetMapping("/devicedata/{id}")
    @ResponseBody
    public List<Device_Data> getSpecificData(@PathVariable String id){
        String DeviceId="Device "+id;
        return deviceDataRepository.findByDevice_Id(DeviceId);
    }

    //used to get the schema of the device data table
    @GetMapping("/schema")
    @ResponseBody
    public List<String> getSchema(){
        return mapService.getSchema();
    }

    //used to get values stored in Dashboard.properties file
    @GetMapping("/properties")
    @ResponseBody
    public Map<String, String> getProperties() {return mapService.getProperties(); }

    @GetMapping("/deviceids")
    @ResponseBody
    public List<String> getAllDeviceIds() {
        return deviceRepository.findAllDeviceIds().stream().map(s->s.substring(7)).collect(Collectors.toList());
    }
}