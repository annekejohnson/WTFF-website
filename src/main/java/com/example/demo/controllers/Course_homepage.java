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
    
    @GetMapping("/courseDisplay")
    public String displayAllCourses(Model model) {

        System.out.println("Getting all courses");
        List<Course> courseList = courseRepository.findAll();
        model.addAttribute("courseDisplay", courseList);
        return "courses/courses";
    }

    @GetMapping("/viewCourse")
    public String seeTheCourse(@RequestParam("courseId") int courseId, Model model) {
    // a feeling that I might be wrong on this ^^ field
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



}
