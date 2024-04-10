package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
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
import com.example.demo.models.User;
import com.example.demo.models.Normalusercourse;
import com.example.demo.models.NormalusercourseRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class NormalusercourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private NormalusercourseRepository normalusercourseRepository;

    @GetMapping("/dashboard")
    public String getAllUserCourses(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("session_user");
        if (currentUser == null) {
            return "redirect:/login";
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
        return "users/pages/userDashboard";
    }

    @Transactional
    @PostMapping("/dropCourse")
    public String dropCourse(@RequestParam("courseId") Integer courseId, HttpSession session) {
        User currentUser = (User) session.getAttribute("session_user");
        if (currentUser != null) {
            normalusercourseRepository.deleteByUsernameAndCourseID(currentUser.getUsername(), courseId);
            return "redirect:/dashboard";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/loadingenroll")
    public String loadEnrollPost(@RequestParam("courseId") int courseId, Model model) {
        Course course = courseRepository.findById(courseId);
        Optional<Course> courseOptional = Optional.ofNullable(course);

        if (courseOptional.isPresent()) {
            Course courseInfo = courseOptional.get();
            model.addAttribute("theCOURSE", courseInfo);
            return "courses/loadingEnroll";
        } else {
            // Handle the case when the provided ID is not found -- unlikely but who knows. countermeasure in place
            return "courses/error";
        }
    }

    @PostMapping("/loadingSpecificEnroll")
    public String loadSpecificEnroll(@RequestBody String entity) {
        return "../dashboard";
    }
    

    @PostMapping("/loadingdelete")
    public String loadDeletePost(@RequestParam("courseId") int courseId, Model model) {
        Course course = courseRepository.findById(courseId);
        Optional<Course> courseOptional = Optional.ofNullable(course);

        if (courseOptional.isPresent()) {
            Course courseInfo = courseOptional.get();
            model.addAttribute("theCOURSE", courseInfo);
            return "courses/loadingDelete";
        } else {
            // Handle the case when the provided ID is not found -- unlikely but who knows. countermeasure in place
            return "courses/error";
        }
    }

    @PostMapping("/enrollCourse")
    public String enrollCourse(@RequestParam("courseId") Integer courseId, HttpSession session) {
        User currentUser = (User) session.getAttribute("session_user");
        if (currentUser != null) {
            Normalusercourse newEnrollment = new Normalusercourse(currentUser.getUsername(), courseId);
            normalusercourseRepository.save(newEnrollment);
            return "redirect:/dashboard";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/userPage")
    public String getUserPage(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("session_user");
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", currentUser);
        return "users/pages/userPage"; 
    }
}






