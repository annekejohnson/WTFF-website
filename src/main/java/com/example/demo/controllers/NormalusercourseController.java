package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.models.Course;
import com.example.demo.models.CourseRepository;
import com.example.demo.models.User;
import com.example.demo.models.Normalusercourse;
import com.example.demo.models.NormalusercourseRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class NormalusercourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private NormalusercourseRepository normalusercourseRepository;

    @GetMapping("/dashboard")
    public String getAllUserCourses(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        List<Normalusercourse> userCourses = normalusercourseRepository.findCoursesByUsername(currentUser.getUsername());
        List<Course> CoursesInBasket = new ArrayList<>();
        List<Course> CoursesInStore = new ArrayList<>();

        for (Course course : courseRepository.findAll()) {
            boolean isEnrolled = false;
            for (Normalusercourse enrollment : userCourses) {
                if (enrollment.getCourseID() == course.getId()) {
                    CoursesInBasket.add(course);
                    isEnrolled = true;
                    break;
                }
            }
            if (!isEnrolled) {
                CoursesInStore.add(course);
            }
        }
        model.addAttribute("CoursesInBasket", CoursesInBasket);
        model.addAttribute("CoursesInStore", CoursesInStore);
        return "/users/pages/userDashboard";
    }

    @Transactional
    @PostMapping("/dropCourse")
    public String dropCourse(@RequestParam("courseId") Integer courseId, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            normalusercourseRepository.deleteByUsernameAndCourseID(currentUser.getUsername(), courseId);
            return "redirect:/dashboard";
        } else {
            return "redirect:/users/login";
        }
    }

    @PostMapping("/enrollCourse")
    public String enrollCourse(@RequestParam("courseId") Integer courseId, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            Normalusercourse newEnrollment = new Normalusercourse(currentUser.getUsername(), courseId);
            normalusercourseRepository.save(newEnrollment);
            return "redirect:/dashboard";
        } else {
            return "redirect:/users/login";
        }
    }

    @GetMapping("/userPage")
    public String getUserPage(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }
        model.addAttribute("user", currentUser);
        return "users/pages/userPage"; 
    }
}






