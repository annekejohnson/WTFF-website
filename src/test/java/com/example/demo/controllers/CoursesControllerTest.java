package com.example.demo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.demo.models.User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing; // Import doNothing
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.models.Course;
import com.example.demo.models.CourseRepository;
import com.example.demo.models.NormalusercourseRepository;

@WebMvcTest(CoursesController.class) // Use WebMvcTest annotation
@AutoConfigureMockMvc
public class CoursesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private NormalusercourseRepository normalusercourseRepository;

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
    //     Course existingCourse = new Course(courseNameToUpdate, "2024-04-01", "Location", "Course Info", "Description", "Image Link");
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
