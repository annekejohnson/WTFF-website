package com.example.demo.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.Course;
import com.example.demo.models.CourseRepository;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class CoursesController {

    @Autowired
    private CourseRepository courseRepo;
    

    @PostMapping("/courses/add")
    public String addCourse(@RequestParam Map<String, String> newCourse, HttpServletResponse response){
        System.out.println("ADD course");
        String newCourseName = newCourse.get("coursename");
        String newLocation = newCourse.get("location");
        String newInfo = newCourse.get("courseinfo");
        String newDescription = newCourse.get("description");
        LocalDateTime newStartdate = LocalDateTime.parse(newCourse.get("startDateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        LocalDateTime newEnddate =  LocalDateTime.parse(newCourse.get("endDateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        courseRepo.save(new Course(newCourseName, newInfo, newStartdate, newEnddate, newLocation, newDescription));
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
    public String updateCourse(@RequestParam String coursename_temp, @RequestParam Map<String, String> updateCourse, HttpServletResponse response, RedirectAttributes redirectAttributes){
        System.out.println("UPDATE course");
        Course course = courseRepo.findByCoursename(coursename_temp);
        if (course == null) {
            return "courses/error";
        }
        if (!updateCourse.get("coursename").isEmpty()) {
            course.setCoursename(updateCourse.get("coursename"));
        }
        if (!updateCourse.get("startDateTime").isEmpty()) {
            LocalDateTime startDateTime = LocalDateTime.parse(updateCourse.get("startDateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            course.setStartdate(startDateTime);
        }
        if (!updateCourse.get("endDateTime").isEmpty()) {
            LocalDateTime endDateTime = LocalDateTime.parse(updateCourse.get("endDateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            course.setEnddate(endDateTime);
        }
        if (!updateCourse.get("location").isEmpty()) {
            course.setLocation(updateCourse.get("location"));
        }
        if (!updateCourse.get("description").isEmpty()) {
            course.setDescription(updateCourse.get("description"));
        }
        if (!updateCourse.get("courseinfo").isEmpty()) {
            course.setCourseinfo(updateCourse.get("courseinfo"));
        }
        courseRepo.save(course);

        response.setStatus(HttpServletResponse.SC_OK);

        return "courses/success";
    }
}
