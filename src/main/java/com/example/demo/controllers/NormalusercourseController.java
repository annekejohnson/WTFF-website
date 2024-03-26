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
        return "users/userDashboard";
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
        return "users/userPage";  // assuming the Thymeleaf template name is userPage.html
    }
}


//     @GetMapping("/test")
// public String testMapping() {
//     System.out.println("Test mapping accessed");
//     return "login"; // Ensure you have a test.html in your templates directory
// }

//     // display the current courses already enrolled in
//     @GetMapping("/dashboard")
//     public String showDashboard(Model model, HttpSession session) 
//     {
//         // retrieve the currently logged-in user info
//         User currentUser = (User) session.getAttribute("currentUser");

//         if (currentUser != null) {
//             // Fetch the enrolled courses for the current user
//             List<Normalusercourse> enrolledCourses = normalusercourseRepository.findCoursesByUsername(currentUser.getUsername());

//             // Create a list to hold complete course details
//             List<Course> CoursesInBasket = new ArrayList<>();
//             List<Course> CoursesInStore = new ArrayList<>();

//             for (Course course : courseRepository.findAll()) 
//             {
//                 boolean isEnrolled = false;
//                 for (Normalusercourse enrollment : enrolledCourses) 
//                 {
//                     if (enrollment.getCourseID() == course.getId()) 
//                     {
//                         CoursesInBasket.add(course);
//                         isEnrolled = true;
//                         break;
//                     }
//                 }
//                 if (!isEnrolled) {
//                     CoursesInStore.add(course);
//                 }
//             }
//             System.out.println("CoursesInBasket size: " + CoursesInBasket.size());
//     System.out.println("CoursesInStore size: " + CoursesInStore.size());


//             // Add complete course details to the model
//             model.addAttribute("enrolledCourses", CoursesInBasket);
//             model.addAttribute("untakenCourses", CoursesInStore);
//         }

//         return "users/userDashboard";
//     }

//     // enrolling courses
//     @PostMapping("/users/enroll")
//     public String enrollUserInCourse(@RequestParam("courseIDs")List<Integer> courseIds, HttpSession session) 
//     {
//         // Find user
//         User user = (User) session.getAttribute("currentUser");

//         if (user != null) {
//             List <Normalusercourse> usercourses = normalusercourseRepository.findCoursesByUsername(user.getUsername());
//             // Iterate through courseIds and enroll the user in each course they chose
//             for (int courseId : courseIds) {
//                 // strategy: compare with previous list of enrolled courses
//                 for (Normalusercourse course : usercourses)
//                 {
//                     if (courseId != course.getCourseID()) // if no matches -> New OR deleted
//                     {
//                         try
//                         {
//                             normalusercourseRepository.deleteByUsernameAndCourseID(user.getUsername(), course.getCourseID());
//                             // if theres no existing course in old repo, means that the course is new
//                         }
//                         catch (Exception e)
//                         {
//                             normalusercourseRepository.save(new Normalusercourse(user.getUsername(), courseId));
//                         }
//                     }
//                 }
//             }
//             //need to explore if CourseID list is empty..
//             return "courses/success"; 
//         }
//         else
//         { return "users/userDashboard"; } //user not found..? less likely but who knows
//     }
// }





