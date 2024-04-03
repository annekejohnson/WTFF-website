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
            // Handle the case when the student with the provided ID is not found -- unlikely but who knows. countermeasure in place
            return "courses/error";
        }
    }


    // this is not on the course homepage but the thymeleaf course specific one

    // user quick enroll (being that the user is in session)
    @GetMapping("/redirection") 
    public String bringToEnrollPage(@RequestParam("courseId") Integer courseId, HttpSession session, Model model) {

        User currentUser = (User) session.getAttribute("currentUser");  // check in session
        Boolean enrolledOrNot = normalusercourseRepository.matchByUsernameAndCourseID(currentUser.getUsername(), courseId);

        if (currentUser != null && !enrolledOrNot) {
            Normalusercourse newEnrollment = new Normalusercourse(currentUser.getUsername(), courseId);
            normalusercourseRepository.save(newEnrollment);
            model.addAttribute("status", "enrollment successful");
            return "redirect:/dashboard";
        } 
        else if (currentUser != null && enrolledOrNot)
        {
            model.addAttribute("status", "You are already enrolled in" + normalusercourseRepository.findByCourseID(courseId).getCoursename());
            //return "redirect:/viewCourse?courseId=" + courseId;
            return "redirect:/dashboard";
        }
        else  // not in session
        {
            model.addAttribute("error", "Please sign in first in order to enroll.");
            return "redirect:/users/login"; 
            // BUT HOW TO MAKE IT SO THAT AFTER LOGGING IN.. IT GOES TO COURSE DASHBOARD..
        }
    }
    
    



}
