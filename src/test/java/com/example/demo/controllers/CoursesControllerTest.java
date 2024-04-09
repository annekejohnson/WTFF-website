package com.example.demo.controllers;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.demo.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import jakarta.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing; // Import doNothing
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.models.Course;
import com.example.demo.models.CourseRepository;
import com.example.demo.models.NormalusercourseRepository;
import com.example.demo.controllers.CoursesController;

@WebMvcTest(CoursesController.class) // Use WebMvcTest annotation
@AutoConfigureMockMvc
public class CoursesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private NormalusercourseRepository normalusercourseRepository;

    private CoursesController controller;
    private MockHttpServletResponse response;

    @Test
    public void testAdminSuccessfullyAddsCourse() throws Exception {
        // Mock a user session with usertype 'admin'
        User adminUser = new User();
        adminUser.setUsertype("admin");

        // Prepare necessary and complete information for the new course
        Map<String, String> newCourse = new HashMap<>();
        newCourse.put("coursename", "New Course");
        newCourse.put("date", "2024-04-01");
        newCourse.put("location", "Location");
        newCourse.put("courseinfo", "Course Info");
        newCourse.put("description", "Course Description");
        newCourse.put("imagelink", "Image Link");

        // Mock the behavior of saving the new course
        when(courseRepository.save(any(Course.class))).thenReturn(new Course());

        // Perform a POST request to add the new course
        mockMvc.perform(MockMvcRequestBuilders.post("/courses/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("coursename", "New Course")
                .param("date", "2024-04-01")
                .param("location", "Location")
                .param("courseinfo", "Course Info")
                .param("description", "Course Description")
                .param("imagelink", "Image Link")
                .sessionAttr("session_user", adminUser))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.view().name("courses/success"));
    }
    

    // @Test
    // public void testAdminSuccessfullyDeletesCourse() throws Exception {
    //     // Mock a user session with usertype 'admin'
    //     User adminUser = new User();
    //     adminUser.setUsertype("admin");

    //     // Prepare the name of the course admin wants to delete
    //     String courseNameToDelete = "CourseToDelete";

    //     // Mock the behavior of finding the course to delete
    //     when(courseRepository.findByCoursename(courseNameToDelete)).thenReturn(new Course(courseNameToDelete, "2024-04-01", "Location", "Course Info", "Description", "Image Link"));

    //     // Mock the behavior of deleting the course
    //     doNothing().when(courseRepository).deleteByCoursename(courseNameToDelete); // Use doNothing

    //     // Perform a POST request to delete the course
    //     mockMvc.perform(MockMvcRequestBuilders.post("/courses/delete")
    //             .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    //             .param("coursename", courseNameToDelete)
    //             .sessionAttr("session_user", adminUser))
    //             .andExpect(MockMvcResultMatchers.status().isOk())
    //             .andExpect(MockMvcResultMatchers.view().name("courses/success"));
    // }

    @Test
    public void testAdminUnsuccessfullyDeletesCourse_NonExistentCourse() throws Exception {
        // Mock a user session with usertype 'admin'
        User adminUser = new User();
        adminUser.setUsertype("admin");

        // Specify the name of the course to delete (non-existent)
        String nonExistentCourseName = "NonExistentCourse";

        // Mock the behavior of finding the course to delete (return null, indicating non-existence)
        when(courseRepository.findByCoursename(nonExistentCourseName)).thenReturn(null);

        // Perform a POST request to delete the non-existent course
        mockMvc.perform(MockMvcRequestBuilders.post("/courses/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("coursename", nonExistentCourseName)
                .sessionAttr("session_user", adminUser))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Expect a successful status
                .andExpect(MockMvcResultMatchers.view().name("courses/error")); // Expect to be redirected to the error page
    }


    @BeforeEach
    public void setUp() {
        courseRepository = mock(CourseRepository.class);
        controller = new CoursesController();
        response = new MockHttpServletResponse();
    }

    @Test
    public void testUpdateValidCourse() {
        String originalName = "Original Course";
        Course course = new Course(originalName, "Info", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Location", "Description");
        when(courseRepository.findByCoursename(originalName)).thenReturn(course);

        Map<String, String> updates = new HashMap<>();
        updates.put("coursename", "Updated Course");
        updates.put("startdate", "2024-01-01T10:00");
        updates.put("enddate", "2024-01-02T10:00");
        updates.put("location", "Updated Location");
        updates.put("description", "Updated Description");
        updates.put("courseinfo", "Updated Info");

        String view = controller.updateCourse(originalName, updates, response);

        assertEquals("courses/success", view);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        verify(courseRepository).save(course); // Verify that the repository's save method was called
        assertEquals("Updated Course", course.getCoursename());
        assertEquals(LocalDateTime.parse("2024-01-01T10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")), course.getStartdate());
    }

    @Test
    public void testCourseNotFound() {
        when(courseRepository.findByCoursename(anyString())).thenReturn(null);

        String view = controller.updateCourse("Nonexistent Course", new HashMap<>(), response);

        assertEquals("courses/error", view);
    }

    @Test
    public void testUpdateWithEmptyFields() {
        String originalName = "Course With Empty Fields";
        Course course = new Course(originalName, "Info", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Location", "Description");
        when(courseRepository.findByCoursename(originalName)).thenReturn(course);

        Map<String, String> updates = new HashMap<>();
        updates.put("coursename", "");
        updates.put("startdate", "");
        updates.put("enddate", "");
        updates.put("location", "");
        updates.put("description", "");
        updates.put("courseinfo", "");

        controller.updateCourse(originalName, updates, response);

        assertEquals(originalName, course.getCoursename()); // No update should happen
        verify(courseRepository, never()).save(any(Course.class)); // Save should not be called
    }

    // @Test
    // public void testAdminSuccessfullyUpdatesCourse() throws Exception {
    //     // Mock a user session with usertype 'admin'
    //     User adminUser = new User();
    //     adminUser.setUsertype("admin");

    //     // Specify the name of the course to update
    //     String courseNameToUpdate = "CourseToUpdate";

    //     // Prepare the new information for the course
    //     String newDate = "2024-04-15";
    //     String newLocation = "New Location";
    //     String newCourseInfo = "New Course Info";

    //     // Mock the behavior of finding the course to update
    //     Course existingCourse = new Course(courseNameToUpdate, "Course Info",LocalDateTime.parse("2024-04-01T00:00"), LocalDateTime.parse("2024-04-01T02:00"), "location", "Description");
    //     when(courseRepository.findByCoursename(courseNameToUpdate)).thenReturn(existingCourse);

    //     // Perform a POST request to update the course
    //     mockMvc.perform(MockMvcRequestBuilders.post("/courses/update")
    //             .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    //             .param("coursename_temp", courseNameToUpdate)
    //             .param("date", newDate)
    //             .param("location", newLocation)
    //             .param("courseinfo", newCourseInfo)
    //             .sessionAttr("session_user", adminUser))
    //             .andExpect(MockMvcResultMatchers.status().isOk()) // Expect a successful status
    //             .andExpect(MockMvcResultMatchers.view().name("courses/success")); // Expect to be redirected to the success page
    // }
}
