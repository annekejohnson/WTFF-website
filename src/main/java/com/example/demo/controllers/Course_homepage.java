package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.Course;
import com.example.demo.models.CourseRepository;
import com.example.demo.models.Normalusercourse;
import com.example.demo.models.NormalusercourseRepository;
import com.example.demo.models.User;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class Course_homepage {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private NormalusercourseRepository normalusercourseRepository;
    

    // this is display all
    @GetMapping("/courseDisplay")
    public String displayAllCourses(Model model) {

        System.out.println("Getting all courses");
        List<Course> courseList = courseRepository.findAll();
        model.addAttribute("courseDisplay", courseList);
        return "courses/courses";
    }

    // this is redirect to see ONE SPECIFIC course's details
    @GetMapping("/viewCourse")
    public String seeTheCourse(@RequestParam("courseId") int courseId, Model model) {
        Course course = courseRepository.findById(courseId);
        Optional<Course> courseOptional = Optional.ofNullable(course);

        if (courseOptional.isPresent()) {
            Course courseInfo = courseOptional.get();
            model.addAttribute("theCOURSE", courseInfo);
            return "courses/specificCourse";
        } else {
            // Handle the case when the provided ID is not found -- unlikely but who knows. countermeasure in place
            return "courses/error";
        }
    }


    // this is not on the course homepage but the thymeleaf course specific one

    // user quick enroll (being that the user is in session)
    @GetMapping("/redirection") 
    public String bringToEnrollPage(@RequestParam("courseId") int courseId, HttpSession session, RedirectAttributes redirectAttributes) {
        try
        {
            Optional<User> currentUserOptional = Optional.ofNullable((User) session.getAttribute("currentUser"));
        
            if (currentUserOptional.isPresent()) {
                User currentUser = currentUserOptional.get(); 
                //boolean enrolledOrNot = normalusercourseRepository.existsByUsernameAndCourseID(currentUser.getUsername(), courseId);
                // trouble maker ^^

                Normalusercourse query= normalusercourseRepository.findByUsernameAndCourseID(currentUser.getUsername(), courseId);
                Course course = courseRepository.findById(courseId);
                String coursename = course.getCoursename();

                if (query == null) { //works normally except for status
                    Normalusercourse newEnrollment = new Normalusercourse(currentUser.getUsername(), courseId);
                    normalusercourseRepository.save(newEnrollment);
                    //practically doing what /enrollCourse does
                    redirectAttributes.addFlashAttribute("status", "successful enrollment in " + coursename);
                    return "redirect:/dashboard";
                } 
                else
                { //when a match is found
                    //Integer creates expectation for Optional<Course> for the courseId - expecting null... int does not <3
                    redirectAttributes.addFlashAttribute("status", "You are already enrolled in " + coursename );
                    return "redirect:/dashboard";
                }
            }
            
            else // not in session -- ASSUMING ONLY NORMAL USERS. NO ADMIN.
            {
                redirectAttributes.addFlashAttribute("error", "Please sign in first in order to enroll.");
                redirectAttributes.addAttribute("courseId", courseId); // Add courseId as a parameter to the login redirect URL
                return "redirect:/loginSpecial"; 
                // DOES NOT WORK IF redirect:/users/login -- gives GET not supported 405 error
            }
        }
        catch ( NullPointerException e)
        {
            // IN CASE... 
            redirectAttributes.addFlashAttribute("error", "Please sign in first in order to enroll.");
            redirectAttributes.addAttribute("courseId", courseId); // Add courseId as a parameter to the login redirect URL
            return "redirect:/loginSpecial"; 
        }
        
    // brings up Request method 'GET' is not supported --> when not logged in...
    }
    



}
