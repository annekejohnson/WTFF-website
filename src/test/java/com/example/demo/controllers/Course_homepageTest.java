package com.example.demo.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(MockitoExtension.class)
public class Course_homepageTest {

    @MockBean
    private UserRepository userRepository;
    
    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private NormalusercourseRepository normalusercourseRepository;

    @MockBean
    HttpSession session;
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HttpServletRequest request;

    @InjectMocks
    private Course_homepage controller;
    
    @Test
    void testBringToEnrollPage_LOGGEDOUT_LOGIN() throws Exception{
        Course course = new Course();
        course.setId(1);
        course.setCoursename("Course 1");

        // when(normalusercourseRepository.findByUsernameAndCourseID("testUser", 1)).thenReturn(null);
        // when(courseRepository.findById(1)).thenReturn(course);

        // HttpSession session = mock(HttpSession.class);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/redirection").param("courseId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/loginSpecial?courseId="+String.valueOf(course.getId())))
                .andExpect(flash().attributeExists("error"));
                // WHEN USER NOT IN SESSION -- links to another mapping in UsersController:
    }

    // @Test
    // void testDisplayAllCourses() throws Exception {
    //     //create mock courses
    //     Course course1 = new Course();
    //     course1.setId(1);
    //     course1.setCoursename("Course 1");
    
    //     Course course2 = new Course();
    //     course2.setId(2);
    //     course2.setCoursename("Course 2");

    //     MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    
    //     mockMvc.perform(get("/courseDisplay"))
    //         .andExpect(status().isOk())
    //         .andExpect(view().name("courses/courses"))
    //         .andExpect(model().attributeExists("courseDisplay"))
    //         .andExpect(model().attribute("courseDisplay", hasSize(2)));
    // }

    // @Test
    // void testSeeTheCourse() throws Exception {
    //     Course course = new Course();
    //     course.setId(1);
    //     course.setCoursename("Course 1");

    //     when(courseRepository.findById(1)).thenReturn(course);

    //     MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    //     mockMvc.perform(get("/viewCourse").param("courseId", "1"))
    //             .andExpect(status().isOk())
    //             .andExpect(model().attributeExists("theCOURSE"))
    //             .andExpect(view().name("courses/specificCourse"));
    // }
}
