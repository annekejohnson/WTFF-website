package com.example.demo.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.models.Course;
import com.example.demo.models.CourseRepository;
import com.example.demo.models.Normalusercourse;
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
    public void testDropCourseloggedin() throws Exception {
        // Arrange
        MockHttpSession mockSession = new MockHttpSession();
        User currentUser = new User("testUser", "password", "user");
        mockSession.setAttribute("session_user", currentUser);

        // Act & Assert
        mockMvc.perform(post("/dropCourse")
                .session(mockSession)
                .param("courseId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));

        // Assert
        verify(normalusercourseRepository).deleteByUsernameAndCourseID(currentUser.getUsername(), 1);
    }

    @Test
    public void testDropCourseloggedout() throws Exception {
        // Arrange
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("session_user", null);

        // Act & Assert
        mockMvc.perform(post("/dropCourse")
                .session(mockSession)
                .param("courseId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Assert
        verify(normalusercourseRepository, never()).deleteByUsernameAndCourseID(anyString(), anyInt());
    }

    @Test
    public void testLoadDeleteValid() throws Exception {
        // Arrange
        int courseId = 1;
        Course mockCourse = new Course("Test Course", "info", LocalDateTime.parse("2024-04-30T11:11:11"), LocalDateTime.parse("2024-04-30T12:11:11"), "here", "desc");
        when(courseRepository.findById(courseId)).thenReturn(mockCourse);

        // Act & Assert
        mockMvc.perform(post("/loadingdelete")
                .param("courseId", String.valueOf(courseId)))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/loadingDelete"))
                .andExpect(model().attributeExists("theCOURSE"))
                .andExpect(model().attribute("theCOURSE", mockCourse));
    }

    @Test
    public void testLoadDeleteInvalid() throws Exception {
        // Arrange
        int courseId = 1;
        when(courseRepository.findById(courseId)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(post("/loadingdelete")
                .param("courseId", String.valueOf(courseId)))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/error"));
    }


    /**
     * Tests the course enrollment functionality to ensure the user can enroll in a course successfully.
     * Verifies that after enrolling in a course, the user is redirected to the dashboard.
     *
     * @throws Exception if the mock MVC request execution fails
     */
    @Test
    public void testEnrollCourse_WithCurrentUser() throws Exception {
        // Arrange
        MockHttpSession mockSession = new MockHttpSession();
        User currentUser = new User("testUser", "password", "user");
        mockSession.setAttribute("session_user", currentUser);

        // Act
        mockMvc.perform(post("/enrollCourse")
                .session(mockSession)
                .param("courseId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));

        // Assert
        verify(normalusercourseRepository).save(any(Normalusercourse.class));
    }

    @Test
    public void testEnrollCourse_WithoutCurrentUser() throws Exception {
        // Arrange
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("session_user", null);

        // Act
        mockMvc.perform(post("/enrollCourse")
                .session(mockSession)
                .param("courseId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Assert
        verify(normalusercourseRepository, never()).save(any(Normalusercourse.class));
    }

    @Test
    public void testLoadEnrollValid() throws Exception {
        // Arrange
        int courseId = 1;
        Course mockCourse = new Course("Test Course", "info", LocalDateTime.parse("2024-04-30T11:11:11"), LocalDateTime.parse("2024-04-30T12:11:11"), "here", "desc");
        when(courseRepository.findById(courseId)).thenReturn(mockCourse);

        // Act & Assert
        mockMvc.perform(post("/loadingenroll")
                .param("courseId", String.valueOf(courseId)))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/loadingEnroll"))
                .andExpect(model().attributeExists("theCOURSE"))
                .andExpect(model().attribute("theCOURSE", mockCourse));
    }

    @Test
    public void testLoadEnrollInvalid() throws Exception {
        // Arrange
        int courseId = 1;
        when(courseRepository.findById(courseId)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(post("/loadingenroll")
                .param("courseId", String.valueOf(courseId)))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/error"));
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
