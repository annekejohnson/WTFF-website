
package com.example.demo.controllers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.models.Course;
import com.example.demo.models.CourseRepository;
import com.example.demo.models.Normalusercourse;
import com.example.demo.models.NormalusercourseRepository;
import com.example.demo.models.User;
import com.example.demo.models.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
public class Course_homepageTest {
    // @Autowired
    // private NormalusercourseRepository normalusercourseRepository;

    @MockBean
    private UserRepository userRepository;
    
    @MockBean
    private CourseRepository courseRepository;

    @InjectMocks
    private Course_homepage controller;

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
    void testBringToEnrollPage_LOGGEDOUT_LOGIN() throws Exception{
        int courseId = 1;
        User currentUser = new User();
        currentUser.setUsername("testUser");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/redirection").param("courseId", String.valueOf(courseId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/loginSpecial?courseId="+String.valueOf(courseId)))
                .andExpect(flash().attributeExists("error"));
                // WHEN USER NOT IN SESSION -- links to another mapping in UsersController:
    }

    @Test
    void testBringToEnrollPage_LOGGEDIN() throws Exception
    {
        int courseId = 1;
        User currentUser = new User();
        currentUser.setUsername("testUser");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", currentUser);

        // when(normalusercourseRepository.findByUsernameAndCourseID(currentUser.getUsername(), courseId)).thenReturn(null);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/redirection").param("courseId", String.valueOf(courseId)).session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"))
                .andExpect(flash().attributeExists("status"));
                // WHEN USER IS IN SESSION
    }

    @Test
    void testDisplayAllCourses() throws Exception{
        List<Course> courses = new ArrayList<>();
        // Add some sample courses to the list

        when(courseRepository.findAll()).thenReturn(courses);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/courseDisplay"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/courses"))
                .andExpect(model().attributeExists("courseDisplay"));
    }

    @Test
    void testSeeTheCourse() throws Exception{
        int courseId = 1;
        Course course = new Course();
        // Set up the course object

        when(courseRepository.findById(courseId)).thenReturn(course);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/viewCourse").param("courseId", String.valueOf(courseId)))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/specificCourse"))
                .andExpect(model().attributeExists("theCOURSE"));
    }
}
