package com.example.demo.controllers;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import com.example.demo.models.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.demo.models.User;
import com.example.demo.models.Normalusercourse;
import com.example.demo.models.NormalusercourseRepository;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private UsersController usersController;
    
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

    private static final String USERNAME = "user1";
    private static final Integer COURSE_ID = 1;

   /**
    * Tests the loading of the sign-up page through a GET request.
    * Ensures that the sign-up page is accessible and returns the correct view name.
    *
    * @throws Exception if the GET request fails
    */
    @Test
    void testSignUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/signUp"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("users/signUp"));
    }


    /**
    * Tests user registration with a POST request to the sign-up endpoint.
     * Verifies that a new user can sign up successfully and checks for the correct view upon successful registration.
    *
    * @throws Exception if the POST request fails
    */
    @Test
    void testSignUpUser() throws Exception {
        when(userRepository.findByUsername("testUser")).thenReturn(null);
        //Map<String, String> requestParams = new HashMap<>();
        //requestParams.put("username", "testUser");
        //requestParams.put("password", "testPassword");
        mockMvc.perform(MockMvcRequestBuilders.post("/users/signUp")
        .param("username", "testUser")
        .param("password", "testPassword"))       
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.view().name("users/feedback/addedUser"));
    }
    //careful ^^ with the password strength validation on signing up -Aril -- I only changed this one method test

    /**
    * Tests the sign-up process with an unavailable username.
    * Ensures that the application correctly handles a sign-up attempt with a username that already exists.
    *
    * @throws Exception if the sign-up process fails
    */
    @Test
    public void testSignUpWithUnavailableUsername() {
        // Arrange
        when(userRepository.findByUsername("existingUser")).thenReturn(new User());
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        // Act
        Map<String, String> newuser = new HashMap<>();
        newuser.put("username", "existingUser");
        newuser.put("password", "password123");
        String view = usersController.signUp(newuser, null, redirectAttributes);

         // Output to console
        System.out.println("View: " + view);
        System.out.println("Error message: " + redirectAttributes.getFlashAttributes().get("error"));

        assertTrue(view.contains("redirect:/users/signUp"), "Redirection to sign up page expected");
        assertNotNull(redirectAttributes.getFlashAttributes().get("error"), "Expected error message not found");
}


    /**
    * Tests accessing the login page with a GET request when the user is not already logged in.
    * Verifies that the login page is rendered correctly.
    *
    * @throws Exception if the GET request fails
    */
    @Test
    void testGetLoginnull() throws Exception {
        when(session.getAttribute("session_user")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }


    
    // @Test
    public void testGetLogin_AdminUser_ReturnsAdminPage() throws Exception {
        User adminUser = new User();
        adminUser.setUsertype("admin");
        when(session.getAttribute("session_user")).thenReturn(adminUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/adminPage"));
    }

    /**
    * Tests the user login functionality with correct credentials for an admin user.
    * Ensures that the correct view is returned for a successful login attempt.
    *
    * @throws Exception if the POST request fails
    */
    @Test
    public void testPostLogin_AdminUser_ReturnsUserPage() throws Exception {
        User nonAdminUser = new User();
        nonAdminUser.setUsertype("admin");
        nonAdminUser.setUsername("AdminUser");
        nonAdminUser.setPassword("password");
        when(userRepository.findByUsernameAndPassword("AdminUser", "password")).thenReturn(List.of(nonAdminUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .param("username", "AdminUser")
                .param("password", "password"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/adminPage"));
    }

    
    /**
    * Tests the user login functionality with correct credentials for a non-admin user.
    * Ensures that the correct view is returned for a successful login attempt.
    *
    * @throws Exception if the POST request fails
    */
    @Test
    public void testPostLogin_NonAdminUser_ReturnsUserPage() throws Exception {
        User nonAdminUser = new User();
        nonAdminUser.setUsertype("user");
        nonAdminUser.setUsername("nonAdminUser");
        nonAdminUser.setPassword("password");
        when(userRepository.findByUsernameAndPassword("nonAdminUser", "password")).thenReturn(List.of(nonAdminUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .param("username", "nonAdminUser")
                .param("password", "password"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/userPage"));
    }

    // @Test 
    // public void missingLoginUser() throws Exception {
    //     Map<String, String> formData = new HashMap<>();
    //     formData.put("password", "testPass");

    //     mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
    //             .param("password", formData.get("password")))
    //             .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
    //             .andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
    //             .andExpect(MockMvcResultMatchers.flash().attributeExists("error"));
    // }

    // @Test
    // public void missingLoginPass() throws Exception {
    //     Map<String, String> formData = new HashMap<>();
    //     formData.put("username", "testUser");

    //     mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
    //             .param("username", formData.get("username")))
    //             .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
    //             .andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
    //             .andExpect(MockMvcResultMatchers.flash().attributeExists("error"));
    // }

    // @Test
    // public void incorrectUserPass() throws Exception {
    //     Map<String, String> formData = new HashMap<>();
    //     formData.put("username", "wronguser");
    //     formData.put("password", "wrongpass");
    //     when(userRepository.findByUsernameAndPassword(formData.get("username"), formData.get("password"))).thenReturn(List.of());
    //     mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
    //             .param("username", formData.get("username"))
    //             .param("password", formData.get("password")))
    //             .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
    //             .andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
    //             .andExpect(MockMvcResultMatchers.flash().attributeExists("error"));
    // }


    /**
    * Tests the logout functionality by invoking the logout URL.
    * Ensures that the user is redirected to the login page after the session is invalidated.
    *
    * @throws Exception if the logout request fails
    */
    @Test
    void testLogout() throws Exception {
        doNothing().when(session).invalidate();
        mockMvc.perform(MockMvcRequestBuilders.get("/logout"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    /**
    * Tests the user deletion confirmation page.
    * Ensures that after a user is deleted, the correct view is displayed.
    *
    * @throws Exception if the GET request fails
    */
    @Test
    void testDel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/deleted"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("users/deleted"));
    }

    /**
    * Tests login functionality when the username is missing.
    * Ensures that the system redirects to the login page and shows an error message.
    *
    * @throws Exception if the login attempt fails
    */
    @Test 
    public void missingLoginUser() throws Exception {
        Map<String, String> formData = new HashMap<>();
        formData.put("password", "testPass");

        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .param("password", formData.get("password")))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("error"));
    }

    /**
    * Tests the login functionality when the password is missing from the request.
    * This test ensures that attempting to log in without providing a password
    * results in a redirection to the login page with an appropriate error message.
    *
    * @throws Exception if the mock MVC request execution fails
    */
    @Test
    public void missingLoginPass() throws Exception {
        Map<String, String> formData = new HashMap<>();
        formData.put("username", "testUser");

        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .param("username", formData.get("username")))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("error"));
    }

    /**
    * Tests the login functionality when an incorrect password is provided.
    * This test verifies that entering a wrong username and password combination
    * results in a redirection to the login page with an error message.
    *
    * @throws Exception if the mock MVC request execution fails
    */
    @Test
    public void incorrectUserPass() throws Exception {
        Map<String, String> formData = new HashMap<>();
        formData.put("username", "wronguser");
        formData.put("password", "wrongpass");
        when(userRepository.findByUsernameAndPassword(formData.get("username"), formData.get("password"))).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .param("username", formData.get("username"))
                .param("password", formData.get("password")))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("error"));
    }

    /**
    * Tests the session invalidation process following the deletion of a user account.
    * This test ensures that after a user account is deleted, the associated session is invalidated,
    * preventing further access to authenticated areas of the application.
    *
    *  @throws Exception if the mock MVC request execution fails
    */
    @Test
    public void testDeleteacc() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    
        User u1 = new User("testuser", "testpass", "user");
        when(userRepository.findByUsername("testuser")).thenReturn(u1);
    
        // Act
        mockMvc.perform(MockMvcRequestBuilders.post("/users/delete/{username}", "testuser"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("users/deleted"));
    
        // Assert
        verify(normalusercourseRepository, times(1)).deleteByUsername("testuser");
        verify(userRepository, times(1)).deleteByUsername("testuser");
}

}