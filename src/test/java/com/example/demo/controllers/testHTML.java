package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class testHTML {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UsersController controller;

    // user story 1 start
    @Test
    public void testGoResources() throws Exception {
        // Perform GET request to "/Resources" endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/Resources"))
                // Expect status code 200 (OK)
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Expect view name to be "pages/resources"
                .andExpect(MockMvcResultMatchers.view().name("pages/resources"));
    }

    @Test
    public void testGoExit() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/Exit"))
        .andExpect(MockMvcResultMatchers.status().isFound())
        .andExpect(MockMvcResultMatchers.view().name("redirect:https://www.theweathernetwork.com/ca"));
    }
    //user story 1 end
}

