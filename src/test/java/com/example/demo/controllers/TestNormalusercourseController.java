package com.example.demo.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.models.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.demo.models.User;
import com.example.demo.models.Course;
import com.example.demo.models.CourseRepository;
import com.example.demo.models.Normalusercourse;
import com.example.demo.models.NormalusercourseRepository;

import static org.mockito.Mockito.when;


@WebMvcTest(Normalusercourse.class)
@AutoConfigureMockMvc
public class TestNormalusercourseController {
    
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

    @MockBean
    private CourseRepository courseRepository;


    // literally everything gives a 404 error on my machine. but i can see that it works online!
    @Test
    public void testGetAllUserCourses_WhenUserLoggedIn_ReturnsUserDashboard() throws Exception {
        User currentUser = new User();
        when(session.getAttribute("currentUser")).thenReturn(currentUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/dashboard"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/userDashboard"));
    }

    @Test
    public void testDropCourseUserNull() throws Exception {
        Course course = new Course();
        when(session.getAttribute("currentUser")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/dropCourse").param("courseID", Integer.toString(course.getId())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users/login"));
    }

    @Test
    void testDropCourse() throws Exception{
        Course course = new Course();
        User currentUser = new User();
        currentUser.setUsername("test");
        Normalusercourse newEnrollment = new Normalusercourse(currentUser.getUsername(), course.getId());
        normalusercourseRepository.save(newEnrollment);
        when(session.getAttribute("currentUser")).thenReturn(currentUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/dropCourse").param("courseID", Integer.toString(course.getId())))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("redirect:/dashboard"));
    }

    @Test
    void testEnroll() throws Exception{
        Course course = new Course();
        User currentUser = new User();
        currentUser.setUsername("test");
        when(session.getAttribute("currentUser")).thenReturn(currentUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollCourse").param("courseID", Integer.toString(course.getId())))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("redirect:/dashboard"));
    }

    @Test
    void testEnrollNull() throws Exception{
        Course course = new Course();
        when(session.getAttribute("currentUser")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollCourse").param("courseID", Integer.toString(course.getId())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users/login"));
    }

    @Test
    void testUserPageNull() throws Exception{
        when(session.getAttribute("currentUser")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/userPage"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users/login"));
    }
    
    @Test
    void testUserPage() throws Exception{
        User currentUser = new User();
        currentUser.setUsername("test");
        when(session.getAttribute("currentUser")).thenReturn(currentUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/userPage"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("users/userPage"))
               .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
               .andExpect(MockMvcResultMatchers.model().attribute("user", currentUser));
    }

}
