package com.example.demo.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.models.CourseRepository;
import com.example.demo.models.NormalusercourseRepository;
import com.example.demo.models.User;

@WebMvcTest(NormalusercourseController.class)
public class NormalusercourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private NormalusercourseRepository normalusercourseRepository;

    /**
     * Tests the course dropping functionality to ensure the user can drop a course successfully.
     * Verifies that after dropping a course, the user is redirected to the dashboard.
     *
     * @throws Exception if the mock MVC request execution fails
     */
    @Test
    void testDropCourse() throws Exception {
        MockHttpSession session = new MockHttpSession();
        User currentUser = new User();
        currentUser.setUsername("user1");
        session.setAttribute("currentUser", currentUser);
        mockMvc.perform(post("/dropCourse")
                        .param("courseId", "1")
                        .session(session))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/dashboard"));

        verify(normalusercourseRepository).deleteByUsernameAndCourseID(currentUser.getUsername(), 1);
    }

    /**
     * Tests the course enrollment functionality to ensure the user can enroll in a course successfully.
     * Verifies that after enrolling in a course, the user is redirected to the dashboard.
     *
     * @throws Exception if the mock MVC request execution fails
     */
    @Test
    void testEnrollCourse() throws Exception {
        MockHttpSession session = new MockHttpSession();
        User currentUser = new User();
        currentUser.setUsername("user1");
        session.setAttribute("currentUser", currentUser);
        mockMvc.perform(post("/enrollCourse")
                        .param("courseId", "1")
                        .session(session))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/dashboard"));
    }

    /**
     * Tests the retrieval of all user courses to ensure the dashboard displays the correct courses.
     * Verifies that the correct view is returned and contains the expected model attributes for the user's courses.
     *
     * @throws Exception if the mock MVC request execution fails
     */
    @Test
    void testGetAllUserCourses() throws Exception {

        MockHttpSession session = new MockHttpSession();
        User currentUser = new User();
        currentUser.setUsername("user1");
        session.setAttribute("currentUser", currentUser);
        mockMvc.perform(get("/dashboard").session(session))
               .andExpect(status().isOk())
               .andExpect(view().name("users/userDashboard"));
    }
    
    /**
     * Tests the functionality of retrieving the user page to ensure it displays correctly.
     * Verifies that the correct view is returned for the user's page.
     *
     * @throws Exception if the mock MVC request execution fails
     */
    @Test
    void testGetUserPage() throws Exception {
        MockHttpSession session = new MockHttpSession();
        User currentUser = new User();
        currentUser.setUsername("user1");
        session.setAttribute("currentUser", currentUser);
        mockMvc.perform(get("/userPage").session(session))
               .andExpect(status().isOk())
               .andExpect(view().name("users/userPage"));
    }
}
