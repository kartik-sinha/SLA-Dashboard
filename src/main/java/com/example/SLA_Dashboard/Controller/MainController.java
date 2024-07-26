package com.example.SLA_Dashboard.Controller;

import com.example.SLA_Dashboard.Model.Device_Data;
import com.example.SLA_Dashboard.Model.Devices;
import com.example.SLA_Dashboard.Model.Location;
import com.example.SLA_Dashboard.Repository.DeviceDataRepository;
import com.example.SLA_Dashboard.Repository.DeviceRepository;
import com.example.SLA_Dashboard.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.SLA_Dashboard.Model.User;
import com.example.SLA_Dashboard.Services.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private SignUpService signUpService;

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

    @GetMapping("/login")
    public String mainPage(Model model) {
        model.addAttribute("error", "");
        return "login";
    }

    @GetMapping("/signUp")
    public String signUpPage(Model model) {
        model.addAttribute("error", "");
        return "signUp";
    }

    @PostMapping("/signUpDashboard")
    public String addUser(@ModelAttribute User user, Model model) {
        String result = signUpService.addUser(user);
        if ("username_taken".equals(result)) {
            model.addAttribute("error", "Username is already taken. Please choose a different one.");
            return "signUp";
        }
        return "redirect:/login";
    }

    @PostMapping("/dashboard")
    public String redirectPage(@ModelAttribute User user, Model model) {
        String result = loginService.verifyUser(user);
        if ("incorrect_password".equals(result)) {
            model.addAttribute("error", "Incorrect password. Please try again.");
            return "login";
        }
        return "dashboard"; // assuming the result is the URL to redirect to
    }

    @GetMapping("/devices")
    @ResponseBody
    public List<Devices> getAllLocations() {
        return deviceRepository.findAll();
    }

    @GetMapping("/devices/{id}")
    @ResponseBody
    public List<Location> getAllLocations(@PathVariable String id) {
        String DeviceId = "Device " + id;
        return locationRepository.findByDevice_Id(DeviceId);
    }

    @GetMapping("/devicedata/{id}")
    @ResponseBody
    public List<Device_Data> getSpecificData(@PathVariable String id) {
        String DeviceId = "Device " + id;
        return deviceDataRepository.findByDevice_Id(DeviceId);
    }

    @GetMapping("/schema")
    @ResponseBody
    public List<String> getSchema() {
        return mapService.getSchema();
    }

    @GetMapping("/properties")
    @ResponseBody
    public Map<String, String> getProperties() {
        return mapService.getProperties();
    }

    @GetMapping("/deviceids")
    @ResponseBody
    public List<String> getAllDeviceIds() {
        return deviceRepository.findAllDeviceIds().stream().map(s -> s.substring(7)).collect(Collectors.toList());
    }
}
