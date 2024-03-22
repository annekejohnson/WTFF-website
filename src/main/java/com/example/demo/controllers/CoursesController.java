package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.models.Course;
import com.example.demo.models.CourseRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class CoursesController {

    @Autowired
    private CourseRepository courseRepo;

    @PostMapping("/courses/add")
    public String addCourse(@RequestParam Map<String, String> newCourse, HttpServletResponse response){
        System.out.println("ADD course");
        String newCourseName = newCourse.get("coursename");
        String newDate = newCourse.get("date");
        String newLocation = newCourse.get("location");
        String newInfo = newCourse.get("courseinfo");
        String newDescription = newCourse.get("description");
        String newLink = newCourse.get("imagelink");
        courseRepo.save(new Course(newCourseName, newDate, newLocation, newInfo, newDescription, newLink));
        response.setStatus(201);
        return "courses/success";
    }

    @PostMapping("/courses/delete")
    @Transactional
    public String deleteTimetable(@RequestParam("coursename") String coursename, HttpServletResponse response) {
        System.out.println("DELETE course");

        Course course = courseRepo.findByCoursename(coursename);
        if (course == null) {
            return "courses/error";
        }

        courseRepo.deleteByCoursename(coursename);
        return "courses/success";
    }

    @PostMapping("/courses/update")
    public String updateStudent(@RequestParam String coursename_temp, @RequestParam Map<String, String> updateCourse, HttpServletResponse response){
        System.out.println("UPDATE course");
        Course course = courseRepo.findByCoursename(coursename_temp);

        if (course == null) {
            return "courses/error";
        }
        if (updateCourse.get("coursename") != "") {
            course.setCoursename(updateCourse.get("coursename"));
        }
        if (updateCourse.get("date") != "") {
            course.setDate(updateCourse.get("date"));
        }
        if (updateCourse.get("description") != "") {
            course.setDescription(updateCourse.get("description"));
        }
        if (updateCourse.get("location") != "") {
            course.setLocation(updateCourse.get("location"));
        }
        if (updateCourse.get("courseinfo") != "") {
            course.setCourseinfo(updateCourse.get("courseinfo"));
        }
        if (updateCourse.get("imagelink") != "") {
            course.setImagelink(updateCourse.get("imagelink"));
        }
        courseRepo.save(course);

        response.setStatus(HttpServletResponse.SC_OK);

        return "courses/success";
    }
}
