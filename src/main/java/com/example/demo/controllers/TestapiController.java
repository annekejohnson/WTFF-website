package com.example.demo.controllers;

import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Test;
import com.example.demo.models.TestRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class TestapiController {

    @Autowired
    private TestRepository testRepo;

    @PostMapping("/tests/add")
    public String addTest(@RequestParam Map<String, String> newTest, HttpServletResponse response){
        System.out.println("ADD test");
        
        // Check if the required keys exist in the map
        // if (!newTest.containsKey("summary") || !newTest.containsKey("startDateTime") || !newTest.containsKey("endDateTime") || !newTest.containsKey("location") || !newTest.containsKey("description")) {
        //     String[] requiredKeys = {"summary", "startdate", "enddate", "location", "description"};

        //     for (String key : requiredKeys) {
        //         if (newTest.containsKey(key)) {
        //             System.out.println("Key '" + key + "' exists in the map.");
        //         } else {
        //             System.out.println("Key '" + key + "' does not exist in the map.");
        //         }
        //     }
        //     // Handle missing parameters
        //     response.setStatus(400); // Bad request
        //     return "courses/error"; // Return an error page or handle the error accordingly
        // }
        
        // Extract parameters from the map
        String newSummary = newTest.get("summary");
        LocalDateTime newStartdate = LocalDateTime.parse(newTest.get("startDateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        LocalDateTime newEnddate =  LocalDateTime.parse(newTest.get("endDateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        String newLocation = newTest.get("location");
        String newDescription = newTest.get("description");

        // Save the event details to the database
        Test newEvent = new Test(newSummary, newStartdate, newEnddate, newLocation, newDescription);
        testRepo.save(newEvent);

        // Set HTTP response status
        response.setStatus(201);
        return "courses/success";
    }
}
