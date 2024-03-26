package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Course;
import com.example.demo.models.CourseRepository;
import com.example.demo.models.User;
import com.example.demo.models.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.example.demo.models.Normalusercourse;
import com.example.demo.models.NormalusercourseRepository;

@Controller
public class NormalusercourseController 
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private NormalusercourseRepository normalusercourseRepository;

    // display the current courses already enrolled in
    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) 
    {
        // retrieve the currently logged-in user info
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            // Fetch the enrolled courses for the current user
            List<Course> enrolledCourses = normalusercourseRepository.findCoursesByUser(currentUser.getUsername());

            // Add enrolled courses to the model
            model.addAttribute("enrolledCourses", enrolledCourses);
        }

        return "users/userPage";
    }

    // enrolling courses
    @PostMapping("/users/enroll")
    public String enrollUserInCourse(@RequestParam("courseIDs")List<Integer> courseIds, HttpSession session) 
    {
        // Find user
        User user = (User) session.getAttribute("currentUser");

        if (user != null) {
            // Iterate through courseIds and enroll the user in each course
            for (int courseId : courseIds) {
                Course course = courseRepository.findById(courseId).orElse(null);
                if (course != null) {
                    // Save enrollment (new course)
                    normalusercourseRepository.save(new Normalusercourse(user.getUsername(), courseId));
                }
                // if want to delete course?
            }
            return "redirect:/users/success"; // no page made yet
        }
        else
        { return "redirect:/users/error"; } //user not found..? less likely but who knows
    }
}
