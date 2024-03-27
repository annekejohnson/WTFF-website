package com.example.demo.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.models.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.demo.models.User;
import com.example.demo.models.NormalusercourseRepository;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(UsersController.class)
@AutoConfigureMockMvc
public class UsersControllerTest {
    
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private NormalusercourseRepository normalusercourseRepository;
    
    @Autowired
    private MockMvc mockMvc;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @MockBean
    HttpSession session;

    @MockBean
    private HttpServletRequest request;

    @Test
    void testGetAllUsers() throws Exception {
        User u1 = new User();
        u1.setUsername("sally");
        u1.setPassword("1234");
        u1.setUsertype("user");
        User u2 = new User();
        u2.setUsername("jane");
        u2.setPassword("1234");
        u2.setUsertype("user");

        List<User> users = new ArrayList<User>();
        users.add(u1);
        users.add(u2);

        when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/view"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("users/showAll"))
            
            .andExpect(MockMvcResultMatchers.model().attribute("us", hasItem(
                allOf(
                    hasProperty("username", Matchers.is("sally")),
                    hasProperty("password", Matchers.is("1234")),
                    hasProperty("usertype", Matchers.is("user"))
                )
            )));
    }

    @Test
    void testSignUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/signUp"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("users/signUp"));
    }


    // user is always found, so account is never created
    // commented code is possibly useful, but right not it doesnt seem to make a difference
    @Test
    void testSignUpUser() throws Exception {
        when(userRepository.findByUsername("testUser")).thenReturn(null);
        //Map<String, String> requestParams = new HashMap<>();
        //requestParams.put("username", "testUser");
        //requestParams.put("password", "testPassword");
        mockMvc.perform(MockMvcRequestBuilders.post("/users/signUp")
        .param("username", "testUser"))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(redirectedUrl("/users/userPage"));
    }

    @Test
    void testGetLoginnull() throws Exception {
        when(session.getAttribute("session_user")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }


    // brings you to login instead of users/adminPage
    @Test
    public void testGetLogin_AdminUser_ReturnsAdminPage() throws Exception {
        User adminUser = new User();
        adminUser.setUsertype("admin");
        when(session.getAttribute("session_user")).thenReturn(adminUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/adminPage"));
    }


    // Brings you to login instead of users/userPage
    @Test
    public void testGetLogin_NonAdminUser_ReturnsUserPage() throws Exception {
        User nonAdminUser = new User();
        nonAdminUser.setUsertype("user");
        when(session.getAttribute("session_user")).thenReturn(nonAdminUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/userPage"));
    }


    @Test
    void testLogout() throws Exception {
        doNothing().when(session).invalidate();
        mockMvc.perform(MockMvcRequestBuilders.get("/logout"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    void testDel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/deleted"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("users/deleted"));
    }


    // never interacts with invalidate apparently
    @Test
    public void testDeleteacc() throws Exception {
        // Mock session invalidate method
        doNothing().when(session).invalidate();

        User u1 = new User("testuser", "testpass", "user");
        userRepository.save(u1);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/delete/{username}", "testuser"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("users/deleted"));

        // Verify that deleteByUsername is called with the correct username
        verify(normalusercourseRepository, times(1)).deleteByUsername("testuser");
        verify(userRepository, times(1)).deleteByUsername("testuser");

        // Verify that the session is invalidated
        verify(session, times(1)).invalidate();
    }
}